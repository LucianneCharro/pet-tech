package br.com.fiap.pettech.dominio.produto.service;

import br.com.fiap.pettech.dominio.produto.entitie.Produto;
import br.com.fiap.pettech.dominio.produto.repository.IProdutoRepository;
import br.com.fiap.pettech.dominio.produto.service.exception.ControllerNotFoundException;
import br.com.fiap.pettech.dominio.produto.service.exception.DataBaseException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProdutoService {

    @Autowired
    private IProdutoRepository repo;


    public Collection<Produto> findAll() {
        var produtos = repo.findAll();

        return produtos;
    }

    public Produto findById(UUID id) {
        var produto = repo.findById(id).orElseThrow(() -> new ControllerNotFoundException("Produto Não Encontrado"));
        return produto;
    }

    public Produto save(Produto produto) {
        var produtoSaved = repo.save(produto);
        return produtoSaved;
    }

    public Produto update(UUID id, Produto produto) {
        try {
            Produto buscarProduto = repo.getOne(id);
            buscarProduto.setNome(produto.getNome());
            buscarProduto.setDescricao(produto.getDescricao());
            buscarProduto.setPreco(produto.getPreco());
            buscarProduto.setUrlImagem(produto.getUrlImagem());
            buscarProduto = repo.save(buscarProduto);
            return buscarProduto;
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Produto não encontrado, " + "id:" + id);
        }
    }

    public void delete(UUID id) {
        var produto = repo.findById(id).orElseThrow(() -> new DataBaseException("Produto Não Encontrado"));
        if (produto != null) {
            repo.deleteById(id);
        }
//        try {
//            repo.deleteById(id);
//        } catch (EmptyResultDataAccessException e) {
//            throw new EntityNotFoundException("Entity not found with ID: " + id);
//        } catch (DataIntegrityViolationException e) {
//            throw new DataBaseException("Entity not found with ID: " + id);
//        }
    }

}
