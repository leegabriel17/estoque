package gerenciador.estoque.converter;

import gerenciador.estoque.model.ProdutoEntity;
import gerenciador.estoque.request.ProdutoRequest;
import gerenciador.estoque.response.ProdutoResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class ProdutoConverterTest {

    @Test
    void deveConverterRequestParaEntityCorretamente() {
        ProdutoRequest request = new ProdutoRequest(1L, "Notebook", "Eletrônicos", 10, new BigDecimal("4500.00"));

        ProdutoEntity entity = ProdutoConverter.toEntity(request);

        assertNotNull(entity);
        assertEquals(request.getNome(), entity.getNome());
        assertEquals(request.getTipo(), entity.getTipo());
        assertEquals(request.getQuantidade(), entity.getQuantidade());
        assertEquals(request.getPreco(), entity.getPreco());
    }

    @Test
    void deveConverterEntityParaResponseCorretamente() {
        ProdutoEntity entity = new ProdutoEntity(1L, "Monitor Gamer", "Periféricos", 25, new BigDecimal("1200.50"));

        ProdutoResponse response = ProdutoConverter.toResponse(entity);

        assertNotNull(response);
        assertEquals(entity.getId(), response.getId());
        assertEquals(entity.getNome(), response.getNome());
        assertEquals(entity.getTipo(), response.getTipo());
        assertEquals(entity.getQuantidade(), response.getQuantidade());
        assertEquals(entity.getPreco(), response.getPreco());
    }

    @Test
    void deveRetornarNullSeEntityForNula() {
        assertNull(ProdutoConverter.toResponse(null));
    }

    @Test
    void deveRetornarNullSeRequestForNulo() {
        assertNull(ProdutoConverter.toEntity(null));
    }
}