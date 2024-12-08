package br.thony.fateczl.colecionaapp.model;

public class Item {

    private int id;
    private String nome;
    private String descricao;
    private String condicao;
    private String valorEstimado;
    private String dataAquisicao;
    private Colecao colecao; // Relacionamento com a Colecao

    // Construtores, getters e setters

    public Item() {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.condicao = condicao;
        this.valorEstimado = valorEstimado;
        this.dataAquisicao = dataAquisicao;
        this.colecao = colecao;
    }

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

    public String getCondicao() {
        return condicao;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }

    public String getValorEstimado() {
        return valorEstimado;
    }

    public void setValorEstimado(String valorEstimado) {
        this.valorEstimado = valorEstimado;
    }

    public String getDataAquisicao() {
        return dataAquisicao;
    }

    public void setDataAquisicao(String dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
    }

    public Colecao getColecao() {
        return colecao;
    }

    public void setColecao(Colecao colecao) {
        this.colecao = colecao;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", colecao='" + colecao.getNome() + '\'' +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", condicao='" + condicao + '\'' +
                ", valorEstimado='" + valorEstimado + '\'' +
                ", dataAquisicao='" + dataAquisicao + '\'' +
                '}';
    }
}
