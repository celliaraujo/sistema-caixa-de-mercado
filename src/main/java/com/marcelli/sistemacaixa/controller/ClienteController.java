package com.marcelli.sistemacaixa.controller;

import com.marcelli.sistemacaixa.service.ClienteService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;



@Component
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @FXML
    private TextField cpfField;

    @FXML
    private TextField nomeField;

    @FXML
    public void salvarCliente() throws Exception{
        String CPF = cpfField.getText();
        String nome = nomeField.getText();
        clienteService.salvar(CPF,nome);
        System.out.println("Cliente salvo: " + nome + " - CPF: " + CPF);
    }



}
