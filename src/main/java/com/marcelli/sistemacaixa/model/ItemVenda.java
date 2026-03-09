package com.marcelli.sistemacaixa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

@Entity
public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private Venda venda;

    private BigDecimal quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal precoUnitarioDesconto;
    private BigDecimal subtotal;
    private BigDecimal subtotalDesconto;

    public ItemVenda(){}

    public Long getId(){
        return id;
    }

    public Produto getProduto(){
        return produto;
    }

    public BigDecimal getQuantidade(){
        return quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public BigDecimal getPrecoUnitarioDesconto() {
        return precoUnitarioDesconto;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getSubtotalDesconto() {
        return subtotalDesconto;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    private void calcularSubtotais(){
        precoUnitario = produto.getPreco();
        precoUnitarioDesconto = produto.getPrecoComDesconto();
        this.subtotal = precoUnitario.multiply(quantidade);
        this.subtotalDesconto = precoUnitarioDesconto.multiply(quantidade);
    }

    public void registraItem(Produto produto, BigDecimal quantidade){
        this.produto = produto;
        setQuantidade(quantidade);
        calcularSubtotais();
    }

    public void setQuantidade(BigDecimal quantidade){
        if(quantidade == null || quantidade.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }
        this.quantidade = quantidade;
    }

}
