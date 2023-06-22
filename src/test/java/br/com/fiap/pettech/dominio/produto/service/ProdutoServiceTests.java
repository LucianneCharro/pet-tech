package br.com.fiap.pettech.dominio.produto.service;

import br.com.fiap.pettech.dominio.produto.dto.ProdutoDto;
import br.com.fiap.pettech.dominio.produto.entitie.Produto;
import br.com.fiap.pettech.dominio.produto.repository.IProdutoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

//testar o contexto da service, não se esta salvando ou buscando dados igual fizemos na jpd
//precisa somente do contexto de configuração do spring estender as classes do contexto do spring
@ExtendWith(SpringExtension.class)
public class ProdutoServiceTests {

    @InjectMocks
    private ProdutoService produtoService;
    @Mock
    private IProdutoRepository repo;

    @Test
    public void findByIdDeveRetornarUmProdutoDtoAoBuscarPorId() {
        UUID uuid = UUID.fromString("0d90bf11-22c9-4826-8ee2-75a57da4d202");
        Produto produto = new Produto();
        produto.setNome("PC");
        produto.setDescricao("PC Game");
        produto.setPreco(50.00);
        produto.setId(null);
        Mockito.when(repo.findById(any())).thenReturn(Optional.of(produto));
        ProdutoDto produtoDto = produtoService.findById(uuid);
        Assertions.assertNotNull(produtoDto);
    }
}
