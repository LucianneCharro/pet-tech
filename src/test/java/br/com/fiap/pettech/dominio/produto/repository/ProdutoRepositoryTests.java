package br.com.fiap.pettech.dominio.produto.repository;

import br.com.fiap.pettech.dominio.produto.dto.ProdutoDto;
import br.com.fiap.pettech.dominio.produto.entitie.Produto;
import br.com.fiap.pettech.dominio.produto.service.exception.ControllerNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;
@DataJpaTest
//carrega os componentes relacionados ao spring data jpa
public class ProdutoRepositoryTests {

    @Autowired
    private IProdutoRepository iProdutoRepository;
    @Test
    public void findByIdDeveRetornarObjetoCasoIdExista(){
        UUID id = UUID.fromString("8f5886eb-29e8-4d38-86d8-7baac90b67c6");
        Optional<Produto> result = iProdutoRepository.findById(id);
        Assertions.assertTrue(result.isPresent());
    }
    @Test
    public void findByIdDeveRetornarControllerNotFoundExceptionCasoIdNaoExista(){
        UUID id = UUID.fromString("8f5886eb-29e8-4d38-86d8-7baac90b6750");
        Optional<Produto> result = iProdutoRepository.findById(id);
        Assertions.assertThrows(ControllerNotFoundException.class , ()-> {
            iProdutoRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException("Produto Não Encontrado"));
        });
    }
    @Test
    public void saveDeveSalvarObjetoCasoIdSejaNull(){
        Produto produto = new Produto();
        produto.setNome("Bolinha");
        produto.setDescricao("Diversão");
        produto.setPreco(10.00);
        produto.setUrlImagem("URL");
        produto.setId(null);
        var produtoSalvo = iProdutoRepository.save(produto);
        Assertions.assertNotNull(produtoSalvo.getId());
    }
}
