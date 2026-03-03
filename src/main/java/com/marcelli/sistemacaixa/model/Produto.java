package com.marcelli.sistemacaixa.model;

import java.math.BigDecimal;

public class Produto {
    private long id;
    private String nome;
    private String marca;
    private BigDecimal preco;
    private double percentualDesconto;

    private Produto(Builder builder){
        this.id = builder.id;
        this.nome = builder.nome;
        this.marca = builder.marca;
        this.preco = builder.preco;
        this.percentualDesconto = builder.percentualDesconto;
    }

    //classe estática interna Builder
    public static class Builder{
        private long id;
        private String nome;
        private String marca;
        private BigDecimal preco;
        private double percentualDesconto;

        public Builder(long id, String nome, BigDecimal preco){
            this.id = id;
            this.nome = nome;
            this.preco = preco;

        }

        public Builder withMarca(String marca){
            this.marca = marca;
            return this;
        }

        public Builder withPercentualDesconto(double percentualDesconto){
            this.percentualDesconto = percentualDesconto;
            return this;
        }

        public Produto build(){
            return new Produto(this);
        }
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public double getPercentualDesconto() {
        return percentualDesconto;
    }

    public void setPercentualDesconto(double percentualDesconto) {
        this.percentualDesconto = percentualDesconto;
    }
}
