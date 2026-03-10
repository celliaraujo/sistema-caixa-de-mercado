package com.marcelli.sistemacaixa.service;

import com.marcelli.sistemacaixa.model.Produto;
import com.marcelli.sistemacaixa.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto salvarProduto(Produto produto){
        verificarDuplicidade(produto);
        return produtoRepository.save(produto);
    }

    public Produto buscarPorId(Long id){
        return produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum produto encontrado com o id: " + id));
    }

    public List<Produto> listarTodos(){
        List<Produto> produtos =  produtoRepository.findAll();
        if(produtos.isEmpty()){
            System.out.println("Nenhum produto encontrado.");
        }
        return produtos;
    }

    public Produto alterarProduto(Long id, Produto produtoAlterado){
        Produto produto = buscarPorId(id);
        if(produtoAlterado.getNome() != null && !produtoAlterado.getNome().isBlank()){
            produto.setNome(produtoAlterado.getNome());
        }
        if(produtoAlterado.getMarca() != null && !produtoAlterado.getMarca().isBlank()){
            produto.setMarca(produtoAlterado.getMarca());
        }
        if(produtoAlterado.getPreco() != null && produtoAlterado.getPreco().compareTo(BigDecimal.ZERO) > 0){
            produto.setPreco(produtoAlterado.getPreco());
        }
        if(produtoAlterado.getPercentualDesconto() > 0 && produtoAlterado.getPercentualDesconto() < 100){
            produto.setPercentualDesconto(produtoAlterado.getPercentualDesconto());
        }
        return produtoRepository.save(produto);
    }

    public void deletarProduto(Long id){
        Produto produto = buscarPorId(id);
        produtoRepository.delete(produto);
    }



    private void verificarDuplicidade(Produto produto){
        boolean existe = produtoRepository.existsByNomeAndMarca(produto.getNome(), produto.getMarca());
        if(existe) throw new IllegalArgumentException("Já existe um produto com este nome e marca.");
    }




}
