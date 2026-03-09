package com.marcelli.sistemacaixa.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "venda")
    private List<ItemVenda> itens = new ArrayList<>();

    @ManyToOne
    private Cliente cliente;

    private BigDecimal valorTotal;
    private LocalDateTime dataVenda;
    private String formaPagamento;

    public Venda(){

    }

    public Venda(Cliente cliente, String formaPagamento){
        this.cliente = cliente;
        this.formaPagamento = formaPagamento;
        this.dataVenda = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public void adicionarItem(Produto produto, BigDecimal quantidade){
        ItemVenda item = new ItemVenda();
        item.registraItem(produto,quantidade);
        item.setVenda(this);
        itens.add(item);
        calcularValorTotal();

    }

    public void calcularValorTotal(){
        BigDecimal resultado = BigDecimal.ZERO;
        for(ItemVenda item : itens){
            resultado = resultado.add(item.getSubtotalDesconto());
        }
        setValorTotal(resultado);
    }


}
