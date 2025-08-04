package gerenciador.estoque.converter;

import gerenciador.estoque.model.ProdutoEntity;
import gerenciador.estoque.request.ProdutoRequest;
import gerenciador.estoque.response.ProdutoResponse;

public class ProdutoConverter {

    public static ProdutoResponse toResponse(ProdutoEntity produto) {
        if (produto == null) return null;

        ProdutoResponse produtoResponse = new ProdutoResponse();
        produtoResponse.setId(produto.getId());
        produtoResponse.setNome(produto.getNome());
        produtoResponse.setTipo(produto.getTipo());
        produtoResponse.setQuantidade(produto.getQuantidade());
        produtoResponse.setPreco(produto.getPreco());
        return produtoResponse;
    }

    public static ProdutoEntity toEntity(ProdutoRequest produto) {
        if (produto == null) return null;

        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setId(produto.getId());
        produtoEntity.setNome(produto.getNome());
        produtoEntity.setTipo(produto.getTipo());
        produtoEntity.setQuantidade(produto.getQuantidade());
        produtoEntity.setPreco(produto.getPreco());
        return produtoEntity;
    }

}

