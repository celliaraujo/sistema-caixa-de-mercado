package com.marcelli.sistemacaixa.repository;

import com.marcelli.sistemacaixa.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    boolean existsByNomeAndMarca(String nome, String marca);
    List<Produto> findByNomeContainingIgnoreCase(String nome);
    List<Produto> findByMarcaContainingIgnoreCase(String marca);
    List<Produto> findBySetorContainingIgnoreCase(String setor);
    List<Produto> findByDescontoPercentual(double desconto);
    List<Produto> findByPreco(BigDecimal preco);
}
