package br.com.fiap.pettech.dominio.produto.controller;

import br.com.fiap.pettech.dominio.produto.dto.ProdutoDto;
import br.com.fiap.pettech.dominio.produto.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProdutoController.class)
// vai colocar o contexto do webmvc pra dentro dela que é onde tem as nossas requisições http requisições com client
// passar a controller que vamos passar pro mvctest
public class ProdutoControllerTest {
    @Autowired
    MockMvc mockmvc;

    @MockBean
    //Mock é quando carrega o contexto é mais rápido e enxuto agora quando não carrega que é o caso do MVC usamos esse
    private ProdutoService produtoService;

    @Test
    public void findByIdDeveRetornarUmProdutoDTOCasoIdExista() throws Exception {
        UUID id = UUID.fromString("0d90bf11-22c9-4826-8ee2-75a57da4d202");
        ProdutoDto produto = new ProdutoDto();
        produto.setNome("PC");
        produto.setDescricao("PC Game");
        produto.setPreco(50.00);
        produto.setId(id);
        Mockito.when(produtoService.findById(id)).thenReturn(produto);
        ResultActions resultActions = mockmvc.perform(get("/produtos/{id}", id).accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
    }
}
