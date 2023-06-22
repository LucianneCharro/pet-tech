package br.com.fiap.pettech.dominio.produto.service;

import br.com.fiap.pettech.dominio.produto.dto.ProdutoDto;
import br.com.fiap.pettech.dominio.produto.entitie.Produto;
import br.com.fiap.pettech.dominio.produto.repository.IProdutoRepository;
import br.com.fiap.pettech.dominio.produto.service.exception.ControllerNotFoundException;
import br.com.fiap.pettech.dominio.produto.service.exception.DataBaseException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProdutoService {

    @Autowired
    private IProdutoRepository repo;


    public Page<ProdutoDto> findAll(PageRequest pagina) {
        var produtos = repo.findAll(pagina);

        return produtos.map(prod -> new ProdutoDto(prod));
    }

    public ProdutoDto findById(UUID id) {
        var produto = repo.findById(id).orElseThrow(() -> new ControllerNotFoundException("Produto Não Encontrado"));
        return new ProdutoDto(produto);
    }

    public ProdutoDto save(ProdutoDto produto) {
        Produto entity = new Produto();
        entity.setNome(produto.getNome());
        entity.setDescricao(produto.getNome());
        entity.setPreco(produto.getPreco());
        entity.setUrlImagem(produto.getUrlImagem());
        var produtoSaved = repo.save(entity);
        return new ProdutoDto(produtoSaved);
    }

    public ProdutoDto update(UUID id, ProdutoDto produto) {
        try {
            Produto buscarProduto = repo.getOne(id);
            buscarProduto.setNome(produto.getNome());
            buscarProduto.setDescricao(produto.getDescricao());
            buscarProduto.setPreco(produto.getPreco());
            buscarProduto.setUrlImagem(produto.getUrlImagem());
            buscarProduto = repo.save(buscarProduto);
            return new ProdutoDto(buscarProduto);
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
