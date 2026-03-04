package com.marcelli.sistemacaixa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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

        public Builder(String nome, BigDecimal preco){
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
        if(percentualDesconto < 0 || percentualDesconto > 100){
            throw new IllegalArgumentException("Desconto deve ser entre 0 e 100%.");
        }
        this.percentualDesconto = percentualDesconto;
    }

    public BigDecimal getPrecoComDesconto(){
        BigDecimal desconto = preco.multiply(BigDecimal.valueOf(percentualDesconto / 100));
        return preco.subtract(desconto);
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", marca='" + marca + '\'' +
                ", preco=" + preco +
                ", percentualDesconto=" + percentualDesconto +
                '}';
    }
}
