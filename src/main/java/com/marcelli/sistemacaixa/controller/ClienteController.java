package com.marcelli.sistemacaixa.controller;

import com.marcelli.sistemacaixa.model.Cliente;
import com.marcelli.sistemacaixa.service.ClienteService;
import jakarta.persistence.EntityNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
    private TextField telefoneField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField buscarCpfField;

    @FXML
    private TableView<Cliente> tabelaClientes;
    @FXML
    private TableColumn<Cliente, String> colunaCpf;
    @FXML
    private TableColumn<Cliente, String> colunaNome;
    @FXML
    private TableColumn<Cliente, String> colunaTelefone;
    @FXML
    private TableColumn<Cliente, String> colunaEmail;

    // Inicializa a tabela ao abrir a tela
    @FXML
    public void initialize() {
        colunaCpf.setCellValueFactory(new PropertyValueFactory<>("CPF"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        carregarClientes();
    }

    @FXML
    public void salvarCliente() {
        if (cpfField.getText().isBlank() || nomeField.getText().isBlank()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Preencha todos os campos.");
            return;
        }

        try {
            String cpf = cpfField.getText();
            String nome = nomeField.getText();
            String telefone = telefoneField.getText();
            String email = emailField.getText();
            clienteService.salvar(cpf, nome, telefone, email);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso",
                    "Cliente salvo: " + nome + " - CPF: " + cpf);

            limparCampos();
            carregarClientes();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro",
                    "Erro ao salvar cliente: " + e.getMessage());
        }
    }

    @FXML
    public void carregarClientes() {
        tabelaClientes.getItems().setAll(clienteService.listarTodos());
    }

    @FXML
    public void selecionarCliente() {
        Cliente selecionado = tabelaClientes.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            cpfField.setText(selecionado.getCPF());
            nomeField.setText(selecionado.getNome());
            telefoneField.setText(selecionado.getTelefone());
            emailField.setText(selecionado.getEmail());
        }
    }

    @FXML
    public void buscarClientePorCpf() {
        try {
            String cpf = buscarCpfField.getText();
            Optional<Cliente> clienteOpt = clienteService.buscarPorCPF(cpf);
            if(clienteOpt.isPresent()) {
                Cliente cliente = clienteOpt.get();
                tabelaClientes.getItems().clear();
                tabelaClientes.getItems().add(cliente);
                cpfField.setText(cliente.getCPF());
                nomeField.setText(cliente.getNome());

            }else{
                throw new EntityNotFoundException("Cliente não encontrado.");
            }

        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro",
                    "Cliente não encontrado: " + e.getMessage());
        }
    }

    @FXML
    public void alterarCliente() {
        Cliente selecionado = tabelaClientes.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            try {
                Cliente dadosAtualizados = new Cliente();

                String cpf = cpfField.getText();
                dadosAtualizados.setNome(nomeField.getText());
                if (cpf != null && !cpf.isBlank()) {
                    dadosAtualizados.setCPF(cpf);
                }
                dadosAtualizados.setTelefone(telefoneField.getText());
                dadosAtualizados.setEmail(emailField.getText());

                clienteService.alterar(selecionado.getId(), dadosAtualizados);

                mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso",
                        "Cliente atualizado com sucesso!");
                carregarClientes();

            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro",
                        "Erro ao atualizar cliente: " + e.getMessage());
            }
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Aviso",
                    "Selecione um cliente na tabela para editar.");
        }
        limparCampos();

    }

    @FXML
    public void excluirCliente() {
        Cliente selecionado = tabelaClientes.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            try {
                clienteService.deletarCliente(selecionado.getId());
                mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso",
                        "Cliente excluído com sucesso!");
                carregarClientes();
            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro",
                        "Erro ao excluir cliente: " + e.getMessage());
            }
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Aviso",
                    "Selecione um cliente na tabela para excluir.");
        }
        limparCampos();
    }

    private void limparCampos() {
        cpfField.clear();
        nomeField.clear();
        telefoneField.clear();
        emailField.clear();
        buscarCpfField.clear();

    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
