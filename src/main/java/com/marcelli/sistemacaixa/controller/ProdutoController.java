package com.marcelli.sistemacaixa.controller;

import com.marcelli.sistemacaixa.model.Cliente;
import com.marcelli.sistemacaixa.model.Produto;
import com.marcelli.sistemacaixa.service.ProdutoService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ApplicationContext springContext;

    public ProdutoController(ProdutoService produtoService, ApplicationContext springContext) {
        this.produtoService = produtoService;
        this.springContext = springContext;
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
    public void abrirMenu(javafx.event.ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu-view.fxml"));
        loader.setControllerFactory(springContext::getBean);

        Scene scene = new Scene(loader.load(), 600, 400);

        // pega a janela atual e troca a cena
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Menu Principal");
        stage.setScene(scene);
        stage.show();
    }

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

            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso",
                    "Produto cadastrado: " + nome);

            limparCampos();
            carregarProdutos();

        }catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro",
                    "Erro ao salvar produto: " + e.getMessage());
        }

    }


    @FXML
    public void atualizarProduto(){}

    @FXML
    public void excluirProduto(){}

    @FXML
    public void filtrarProduto(){
        String setor = setorField.getText();
        String nome = nomeField.getText();
        String marca = marcaField.getText();
        String precoStr = precoField.getText();
        String descontoStr = descontoField.getText();

        BigDecimal precoFiltro = null;
        Double descontoFiltro = null;

        try {
            if (precoStr != null && !precoStr.isBlank()) {
                precoFiltro = new BigDecimal(precoStr);
            }
            if (descontoStr != null && !descontoStr.isBlank()) {
                descontoFiltro = Double.parseDouble(descontoStr);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Preço ou desconto inválido.");
            return;
        }

        final BigDecimal precoFinal = precoFiltro;
        final Double descontoFinal = descontoFiltro;

        List<Produto> filtrados = produtoService.listarTodos().stream()
                .filter(p -> setor == null || setor.isBlank() || p.getSetor().equalsIgnoreCase(setor))
                .filter(p -> nome == null || nome.isBlank() || p.getNome().toLowerCase()
                .contains(nome.toLowerCase()))
                .filter(p -> marca == null || marca.isBlank() || p.getMarca().equalsIgnoreCase(marca))
                .filter(p -> precoFinal == null || p.getPreco().compareTo(precoFinal) == 0) // igual ao preço digitado
                .filter(p -> descontoFinal == null || p.getDesconto() == descontoFinal) // igual ao desconto digitado
                .toList();

        tabelaProdutos.getItems().setAll(filtrados);
    }

    @FXML
    public void carregarProdutos() {
        tabelaProdutos.getItems().setAll(produtoService.listarTodos());
    }

    @FXML
    public void selecionarProduto() {
        Produto selecionado = tabelaProdutos.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            setorField.setText(selecionado.getSetor());
            nomeField.setText(selecionado.getNome());
            marcaField.setText(selecionado.getMarca());
            precoField.setText(selecionado.getPreco().toString());
            descontoField.setText(String.valueOf(selecionado.getDesconto()));
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
