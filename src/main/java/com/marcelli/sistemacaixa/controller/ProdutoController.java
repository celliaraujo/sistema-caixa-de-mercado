package com.marcelli.sistemacaixa.controller;

import com.marcelli.sistemacaixa.model.Cliente;
import com.marcelli.sistemacaixa.model.Produto;
import com.marcelli.sistemacaixa.service.ProdutoService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Component;
import javafx.scene.control.TextField;

import java.math.BigDecimal;

@Component
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @FXML
    private TextField setorField;

    @FXML
    private TextField nomeField;

    @FXML
    private TextField marcaField;

    @FXML
    private TextField precoField;

    @FXML
    private TextField descontoField;

    @FXML
    private TableView<Produto> tabelaProdutos;
    @FXML
    private TableColumn<Produto, String> colunaSetor;
    @FXML
    private TableColumn<Produto, String> colunaNome;
    @FXML
    private TableColumn<Produto, String> colunaMarca;
    @FXML
    private TableColumn<Produto, String> colunaPreco;
    @FXML
    private TableColumn<Produto, String> colunaDesconto;

    @FXML
    public void initialize() {
        colunaSetor.setCellValueFactory(new PropertyValueFactory<>("setor"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colunaDesconto.setCellValueFactory(new PropertyValueFactory<>("desconto"));
        carregarProdutos();
    }

    @FXML
    public void carregarProdutos() {
        tabelaProdutos.getItems().setAll(produtoService.listarTodos());
    }

    @FXML
    public void salvarProduto(){
        try{
            String setor = setorField.getText();
            String nome = nomeField.getText();
            String marca = marcaField.getText();
            BigDecimal preco = new BigDecimal(precoField.getText());
            double desconto = Double.parseDouble(descontoField.getText());

            Produto novoProduto = new Produto.Builder(nome, preco, setor)
                    .withMarca(marca)
                    .withPercentualDesconto(desconto)
                    .build();
            produtoService.salvarProduto(novoProduto);

            limparCampos();



        }catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro",
                    "Erro ao salvar produto: " + e.getMessage());
        }

    }

    private void limparCampos() {
        setorField.clear();
        nomeField.clear();
        marcaField.clear();
        precoField.clear();
        descontoField.clear();

    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
