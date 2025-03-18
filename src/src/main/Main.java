package main;

import view.ClienteCRUD;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Configurar aparência do Swing para o sistema local (opcional)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Inicializar a aplicação com a interface principal
        SwingUtilities.invokeLater(() -> {
            ClienteCRUD clienteApp = new ClienteCRUD();
            clienteApp.setVisible(true);
        });

        // Caso futuramente precise realizar inicializações adicionais, adicione aqui
    }
}