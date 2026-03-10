package com.marcelli.sistemacaixa.service;

import com.marcelli.sistemacaixa.model.Cliente;
import com.marcelli.sistemacaixa.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public void salvar(String CPF, String nome){
        Cliente cliente = new Cliente(CPF, nome);
        clienteRepository.save(cliente);
    }

    public Cliente buscarPorId(Long id){
        return clienteRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Cliente não encontrado com o id: " + id));
    }

    public List<Cliente> listarTodos(){
        List<Cliente> clientes = clienteRepository.findAll();
        if(clientes.isEmpty()){
            System.out.println("Nenhum cliente encontrado.");
        }
        return clientes;
    }

    public List<Cliente> buscarPorNome(String nome){
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Cliente alterar(Long id, Cliente dadosAtualizados){
        Cliente clienteExistente = buscarPorId(id);
        if(dadosAtualizados.getNome() != null && !dadosAtualizados.getNome().isBlank()){
            clienteExistente.setNome(dadosAtualizados.getNome());
        }
        if(dadosAtualizados.getCPF() != null && !dadosAtualizados.getCPF().isBlank()){
            clienteExistente.setCPF(dadosAtualizados.getCPF());
        }

        return clienteRepository.save(clienteExistente);
    }

    public void deletarCliente(Long id){
        Cliente cliente = buscarPorId(id);
        clienteRepository.delete(cliente);
    }
}
