package com.marcelli.sistemacaixa.service;

import com.marcelli.sistemacaixa.model.Cliente;
import com.marcelli.sistemacaixa.repository.ClienteRepository;
import org.springframework.stereotype.Service;

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
}
