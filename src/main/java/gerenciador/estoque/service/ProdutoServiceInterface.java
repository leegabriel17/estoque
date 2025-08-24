package gerenciador.estoque.service;

import gerenciador.estoque.request.ProdutoRequest;
import gerenciador.estoque.response.ProdutoResponse;

import java.util.List;

public interface ProdutoServiceInterface {
    ProdutoResponse salvar(ProdutoRequest produtoRequest);

    ProdutoResponse atualizar(Long id, ProdutoRequest produtoRequest);

    void deletar(Long id);

    List<ProdutoResponse> listarTodos();

    ProdutoResponse buscarPorId(Long id);
}