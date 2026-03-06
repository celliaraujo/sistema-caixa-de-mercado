package com.marcelli.sistemacaixa.repository;

import com.marcelli.sistemacaixa.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
