package view;

import dao.ClienteDAO;
import model.Cliente;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ClienteCRUD extends JFrame {
    private final ClienteDAO<Cliente> dao = new ClienteDAO<>();
    private JTextField txtId, txtNome, txtEmail, txtTelefone, txtNascimento;
    private JTextArea txtArea;

    public ClienteCRUD() {
        configurarJanela();
        inicializarComponentes();
        carregarClientes();
    }

    private void configurarJanela() {
        setTitle("CRUD de Clientes");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Aplicar aparência moderna ao Swing
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void inicializarComponentes() {
        JPanel painel = new JPanel(new BorderLayout());
        JPanel painelCampos = new JPanel(new GridLayout(5, 2));

        // Campos de entrada
        txtId = new JTextField();
        txtNome = new JTextField();
        txtEmail = new JTextField();
        txtTelefone = new JTextField();
        txtNascimento = new JTextField();

        painelCampos.add(new JLabel("ID:"));
        painelCampos.add(txtId);
        painelCampos.add(new JLabel("Nome:"));
        painelCampos.add(txtNome);
        painelCampos.add(new JLabel("Email:"));
        painelCampos.add(txtEmail);
        painelCampos.add(new JLabel("Telefone:"));
        painelCampos.add(txtTelefone);
        painelCampos.add(new JLabel("Nascimento (dd/mm/aaaa):"));
        painelCampos.add(txtNascimento);

        // Botões
        JPanel painelBotoes = new JPanel();
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnRemover = new JButton("Remover");
        JButton btnListar = new JButton("Listar Todos");

        txtArea = new JTextArea();
        JScrollPane scroll = new JScrollPane(txtArea);

        // Ações
        btnAdicionar.addActionListener(e -> adicionarCliente());
        btnBuscar.addActionListener(e -> buscarCliente());
        btnAtualizar.addActionListener(e -> atualizarCliente());
        btnRemover.addActionListener(e -> removerCliente());
        btnListar.addActionListener(e -> listarClientes());

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnBuscar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnListar);

        painel.add(painelCampos, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);
        painel.add(painelBotoes, BorderLayout.SOUTH);

        add(painel);
    }

    private void adicionarCliente() {
        if (camposVazios()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos!");
            return;
        }

        try {
            int id = Integer.parseInt(txtId.getText());
            List<Cliente> clientes = dao.carregar();

            for (Cliente c : clientes) {
                if (c.getId() == id) {
                    JOptionPane.showMessageDialog(this, "ID já existente!");
                    return;
                }
            }

            Cliente cliente = new Cliente(
                    id,
                    txtNome.getText(),
                    txtEmail.getText(),
                    txtTelefone.getText(),
                    txtNascimento.getText()
            );

            clientes.add(cliente);
            dao.salvar(clientes);
            limparCampos();
            listarClientes();
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido!");
        }
    }

    private void buscarCliente() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo ID deve ser preenchido!");
            return;
        }

        try {
            int id = Integer.parseInt(txtId.getText());
            List<Cliente> clientes = dao.carregar();

            for (Cliente c : clientes) {
                if (c.getId() == id) {
                    preencherCampos(c);
                    return;
                }
            }

            JOptionPane.showMessageDialog(this, "Cliente não encontrado!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido!");
        }
    }

    private void atualizarCliente() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo ID deve ser preenchido!");
            return;
        }

        try {
            int id = Integer.parseInt(txtId.getText());
            List<Cliente> clientes = dao.carregar();

            for (Cliente c : clientes) {
                if (c.getId() == id) {
                    c.setNome(txtNome.getText());
                    c.setEmail(txtEmail.getText());
                    c.setTelefone(txtTelefone.getText());
                    c.setDataNascimento(txtNascimento.getText());
                    dao.salvar(clientes);
                    limparCampos();
                    listarClientes();
                    JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!");
                    return;
                }
            }

            JOptionPane.showMessageDialog(this, "Cliente não encontrado!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido!");
        }
    }

    private void removerCliente() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo ID deve ser preenchido!");
            return;
        }

        try {
            int id = Integer.parseInt(txtId.getText());
            List<Cliente> clientes = dao.carregar();

            if (clientes.removeIf(c -> c.getId() == id)) {
                dao.salvar(clientes);
                limparCampos();
                listarClientes();
                JOptionPane.showMessageDialog(this, "Cliente removido com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Cliente não encontrado!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido!");
        }
    }

    private void listarClientes() {
        List<Cliente> clientes = dao.carregar();
        if (clientes.isEmpty()) {
            txtArea.setText("Nenhum cliente cadastrado no momento.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Cliente c : clientes) {
            sb.append(c.toString()).append("\n");
        }

        txtArea.setText(sb.toString());
    }

    private boolean camposVazios() {
        return txtId.getText().isEmpty() || txtNome.getText().isEmpty() ||
                txtEmail.getText().isEmpty() || txtTelefone.getText().isEmpty() ||
                txtNascimento.getText().isEmpty();
    }

    private void preencherCampos(Cliente cliente) {
        txtId.setText(String.valueOf(cliente.getId()));
        txtNome.setText(cliente.getNome());
        txtEmail.setText(cliente.getEmail());
        txtTelefone.setText(cliente.getTelefone());
        txtNascimento.setText(cliente.getDataNascimento());
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtNascimento.setText("");
    }

    private void carregarClientes() {
        listarClientes();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClienteCRUD().setVisible(true));
    }
}