package dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO<Cliente> {
    private static final String ARQUIVO = "clientes.dat";

    public void salvar(List<Cliente> clientes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(clientes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Cliente> carregar() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO))) {
            return (List<Cliente>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}