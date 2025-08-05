package gerenciador.estoque.service;

import gerenciador.estoque.converter.ProdutoConverter;
import gerenciador.estoque.model.ProdutoEntity;
import gerenciador.estoque.exception.ProdutoNaoEncontradoException;
import gerenciador.estoque.repository.ProdutoRepository;
import gerenciador.estoque.request.ProdutoRequest;
import gerenciador.estoque.response.ProdutoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoResponse salvar(ProdutoRequest produtoRequest) {
        return ProdutoConverter.toResponse(produtoRepository.save(ProdutoConverter.toEntity(produtoRequest)));
    }

    public ProdutoResponse atualizar(Long id, ProdutoRequest produtoRequest) {
        ProdutoEntity produtoEntity = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto com ID " + id + " não encontrado."));

        produtoEntity.setNome(produtoRequest.getNome());
        produtoEntity.setTipo(produtoRequest.getTipo());
        produtoEntity.setPreco(produtoRequest.getPreco());
        produtoEntity.setQuantidade(produtoRequest.getQuantidade());
        log.info("Produto atualizado com sucesso: {}", produtoEntity);
        return ProdutoConverter.toResponse(produtoRepository.save(produtoEntity));
    }

    public void deletar(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ProdutoNaoEncontradoException("Produto com ID " + id + " não encontrado para exclusão.");
        }
        produtoRepository.deleteById(id);
    }

    public List<ProdutoResponse> listarTodos() {
        return produtoRepository.findAll().stream()
                .map(ProdutoConverter::toResponse)
                .toList();
    }

    public ProdutoResponse buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .map(ProdutoConverter::toResponse)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto com ID " + id + " não encontrado."));
    }
}