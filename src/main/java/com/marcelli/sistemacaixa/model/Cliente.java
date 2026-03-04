package com.marcelli.sistemacaixa.model;

import com.marcelli.sistemacaixa.util.ValidaCPF;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String CPF;
    private String nome;

    public Cliente(String CPF, String nome){
        setCPF(CPF);
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        if(!ValidaCPF.isCPF(CPF)){
            throw new IllegalArgumentException("CPF inválido");
        }
        this.CPF = CPF;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", CPF='" + CPF + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }
}
