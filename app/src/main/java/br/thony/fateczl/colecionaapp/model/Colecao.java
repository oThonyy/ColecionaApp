package br.thony.fateczl.colecionaapp.model;

public class Colecao {

    private int id;
    private String nome;
    private String descricao;
    private String categoria;
    private String dataCriacao;

    public Colecao() {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.dataCriacao = dataCriacao;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public String toString() {
        return "id = " + id +
                "\nnome = " + nome +
                "\ndescricao = " + descricao +
                "\ncategoria = " + categoria +
                "\ndataCriacao = " + dataCriacao + "\n";
    }
}
