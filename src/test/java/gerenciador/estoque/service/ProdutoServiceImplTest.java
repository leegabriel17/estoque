package gerenciador.estoque.service;

import gerenciador.estoque.exception.ProdutoDuplicadoException;
import gerenciador.estoque.exception.ProdutoNaoEncontradoException;
import gerenciador.estoque.model.ProdutoEntity;
import gerenciador.estoque.repository.ProdutoRepository;
import gerenciador.estoque.request.ProdutoRequest;
import gerenciador.estoque.response.ProdutoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceImplTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoServiceInterface produtoServiceInterface;
    private ProdutoEntity produtoEntity;
    private ProdutoRequest produtoRequest;

    @BeforeEach
    void setUp() {
        produtoEntity = new ProdutoEntity(1L, "Teclado Mecânico", "Periféricos", 50, new BigDecimal("350.00"));
        produtoRequest = new ProdutoRequest(1L, "Teclado Mecânico", "Periféricos", 50, new BigDecimal("350.00"));
    }

    @Test
    void deveSalvarProdutoComSucesso() {
        when(produtoRepository.existsByNomeIgnoreCase(anyString())).thenReturn(false);
        when(produtoRepository.save(any(ProdutoEntity.class))).thenReturn(produtoEntity);

        ProdutoResponse response = produtoServiceInterface.salvar(produtoRequest);

        assertNotNull(response);
        assertEquals("Teclado Mecânico", response.getNome());
        verify(produtoRepository, times(1)).save(any(ProdutoEntity.class));
    }

    @Test
    void deveLancarExcecaoAoSalvarProdutoComNomeDuplicado() {
        when(produtoRepository.existsByNomeIgnoreCase(anyString())).thenReturn(true);

        assertThrows(ProdutoDuplicadoException.class, () -> {
            produtoServiceInterface.salvar(produtoRequest);
        });

        verify(produtoRepository, never()).save(any(ProdutoEntity.class));
    }

    @Test
    void deveAtualizarProdutoComSucesso() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoEntity));
        when(produtoRepository.save(any(ProdutoEntity.class))).thenReturn(produtoEntity);

        ProdutoResponse response = produtoServiceInterface.atualizar(1L, produtoRequest);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        verify(produtoRepository, times(1)).save(produtoEntity);
    }

    @Test
    void deveLancarExcecaoAoAtualizarParaUmNomeDuplicado() {
        ProdutoEntity outroProduto = new ProdutoEntity(2L, "Outro Produto", "Tipo", 1, BigDecimal.ONE);
        produtoRequest.setNome("Outro Produto");

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoEntity));
        when(produtoRepository.findByNomeIgnoreCase("Outro Produto")).thenReturn(Optional.of(outroProduto));

        assertThrows(ProdutoDuplicadoException.class, () -> {
            produtoServiceInterface.atualizar(1L, produtoRequest);
        });
    }

    @Test
    void deveLancarExcecaoAoAtualizarProdutoNaoEncontrado() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProdutoNaoEncontradoException.class, () -> {
            produtoServiceInterface.atualizar(1L, produtoRequest);
        });

        verify(produtoRepository, never()).save(any(ProdutoEntity.class));
    }

    @Test
    void deveDeletarProdutoComSucesso() {
        when(produtoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(produtoRepository).deleteById(1L);

        produtoServiceInterface.deletar(1L);

        verify(produtoRepository, times(1)).deleteById(1L);
    }

    @Test
    void deveLancarExcecaoAoDeletarProdutoNaoEncontrado() {
        when(produtoRepository.existsById(1L)).thenReturn(false);

        assertThrows(ProdutoNaoEncontradoException.class, () -> {
            produtoServiceInterface.deletar(1L);
        });

        verify(produtoRepository, never()).deleteById(anyLong());
    }

    @Test
    void deveListarTodosOsProdutos() {
        when(produtoRepository.findAll()).thenReturn(List.of(produtoEntity));

        List<ProdutoResponse> responses = produtoServiceInterface.listarTodos();

        assertFalse(responses.isEmpty());
        assertEquals(1, responses.size());
        verify(produtoRepository, times(1)).findAll();
    }

    @Test
    void deveBuscarProdutoPorIdComSucesso() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoEntity));

        ProdutoResponse response = produtoServiceInterface.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        verify(produtoRepository, times(1)).findById(1L);
    }

    @Test
    void deveLancarExcecaoAoBuscarProdutoPorIdNaoEncontrado() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProdutoNaoEncontradoException.class, () -> {
            produtoServiceInterface.buscarPorId(1L);
        });
    }
}