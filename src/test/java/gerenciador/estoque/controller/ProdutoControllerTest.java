package gerenciador.estoque.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gerenciador.estoque.exception.ProdutoDuplicadoException;
import gerenciador.estoque.exception.ProdutoNaoEncontradoException;
import gerenciador.estoque.request.ProdutoRequest;
import gerenciador.estoque.response.ProdutoResponse;
import gerenciador.estoque.service.ProdutoServiceImpl;
import gerenciador.estoque.service.ProdutoServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProdutoController.class)
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProdutoServiceInterface produtoServiceInterface;

    private ProdutoRequest produtoRequest;
    private ProdutoResponse produtoResponse;

    @BeforeEach
    void setUp() {
        produtoRequest = new ProdutoRequest(null, "Mouse Gamer", "Periféricos", 100, new BigDecimal("150.00"));
        produtoResponse = new ProdutoResponse(1L, "Mouse Gamer", "Periféricos", 100, new BigDecimal("150.00"));
    }

    @Test
    void deveSalvarProdutoERetornarStatus201() throws Exception {
        when(produtoServiceInterface.salvar(any(ProdutoRequest.class))).thenReturn(produtoResponse);

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Mouse Gamer"))
                .andExpect(header().string("Location", "http://localhost/produtos/1"));
    }

    @Test
    void deveListarTodosOsProdutosERetornarStatus200() throws Exception {
        when(produtoServiceInterface.listarTodos()).thenReturn(List.of(produtoResponse));

        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Mouse Gamer"));
    }

    @Test
    void deveBuscarProdutoPorIdERetornarStatus200() throws Exception {
        when(produtoServiceInterface.buscarPorId(1L)).thenReturn(produtoResponse);

        mockMvc.perform(get("/produtos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void deveRetornarStatus404AoBuscarProdutoInexistente() throws Exception {
        when(produtoServiceInterface.buscarPorId(99L)).thenThrow(new ProdutoNaoEncontradoException("Produto não encontrado"));

        mockMvc.perform(get("/produtos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornarStatus400AoSalvarProdutoComNomeInvalido() throws Exception {
        produtoRequest.setNome(""); // Nome inválido

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors.nome").exists());
    }

    @Test
    void deveRetornarStatus409AoSalvarProdutoDuplicado() throws Exception {
        when(produtoServiceInterface.salvar(any(ProdutoRequest.class)))
                .thenThrow(new ProdutoDuplicadoException("Produto já existe"));

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Conflito de Dados"));
    }

    @Test
    void deveRetornarStatus400AoSalvarProdutoComPrecoNegativo() throws Exception {
        produtoRequest.setPreco(new BigDecimal("-10.00")); // Preço inválido

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors.preco").value("O preço deve ser um valor positivo."));
    }

    @Test
    void deveRetornarStatus400AoSalvarProdutoComQuantidadeZero() throws Exception {
        produtoRequest.setQuantidade(0); // Quantidade inválida

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors.quantidade").value("A quantidade deve ser um número positivo."));
    }

    @Test
    void deveAtualizarProdutoERetornarStatus200() throws Exception {
        when(produtoServiceInterface.atualizar(eq(1L), any(ProdutoRequest.class))).thenReturn(produtoResponse);

        mockMvc.perform(put("/produtos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Mouse Gamer"));
    }

    @Test
    void deveDeletarProdutoERetornarStatus204() throws Exception {
        doNothing().when(produtoServiceInterface).deletar(1L);

        mockMvc.perform(delete("/produtos/1"))
                .andExpect(status().isNoContent());
    }
}