package com.marcelli.sistemacaixa.repository;

import com.marcelli.sistemacaixa.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    boolean existsByNomeAndMarca(String nome, String marca);
}
