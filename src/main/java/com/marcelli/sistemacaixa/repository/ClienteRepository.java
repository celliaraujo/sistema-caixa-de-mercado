package com.marcelli.sistemacaixa.repository;

import com.marcelli.sistemacaixa.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

   List<Cliente> findByNomeContainingIgnoreCase(String nome);
   boolean existsByCPF(String cpf);
   Optional<Cliente> findByCPF(String CPF);
}
