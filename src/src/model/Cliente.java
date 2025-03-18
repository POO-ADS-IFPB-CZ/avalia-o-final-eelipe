package model;

import java.io.Serializable;

public class Cliente implements Serializable {
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String dataNascimento;
    private String dataCadastro;

    // Construtor
    public Cliente(int id, String nome, String email, String telefone, String dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.dataCadastro = new java.util.Date().toString(); // Data de cadastro automática
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\nNome: " + nome + "\nEmail: " + email +
                "\nTelefone: " + telefone + "\nNascimento: " + dataNascimento +
                "\nCadastro: " + dataCadastro + "\n";
    }
}