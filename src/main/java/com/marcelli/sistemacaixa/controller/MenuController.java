package com.marcelli.sistemacaixa.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MenuController {

    private final ApplicationContext springContext;

    public MenuController(ApplicationContext springContext) {
        this.springContext = springContext;
    }

    @FXML
    public void abrirCadastroCliente(javafx.event.ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cliente-view.fxml"));
        loader.setControllerFactory(springContext::getBean);

        Scene scene = new Scene(loader.load(), 600, 400);

        // pega a janela atual e troca a cena
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Cadastro de Cliente");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void abrirCadastroProduto() throws Exception{
        System.out.println("Botão de Cliente clicado!");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/produto-view.fxml"));
        Scene scene = new Scene(loader.load(),600,400);
        Stage stage = new Stage();
        stage.setTitle("Cadastro de Produto");
        stage.setScene(scene);
        stage.show();
    }
}
