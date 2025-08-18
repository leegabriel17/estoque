package gerenciador.estoque.service;

import gerenciador.estoque.converter.ProdutoConverter;
import gerenciador.estoque.model.ProdutoEntity;
import gerenciador.estoque.exception.ProdutoDuplicadoException;
import gerenciador.estoque.exception.ProdutoNaoEncontradoException;
import gerenciador.estoque.repository.ProdutoRepository;
import gerenciador.estoque.request.ProdutoRequest;
import gerenciador.estoque.response.ProdutoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProdutoService {

    @Autowired
    private final ProdutoRepository produtoRepository;

    public ProdutoResponse salvar(ProdutoRequest produtoRequest) {
        if (produtoRepository.existsByNomeIgnoreCase(produtoRequest.getNome())) {
            throw new ProdutoDuplicadoException("Já existe um produto cadastrado com o nome: " + produtoRequest.getNome());
        }
        return ProdutoConverter.toResponse(produtoRepository.save(ProdutoConverter.toEntity(produtoRequest)));
    }

    public ProdutoResponse atualizar(Long id, ProdutoRequest produtoRequest) {
        ProdutoEntity produtoEntity = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto com ID " + id + " não encontrado."));

        // Verifica se o novo nome já pertence a OUTRO produto
        produtoRepository.findByNomeIgnoreCase(produtoRequest.getNome())
                .ifPresent(produtoExistente -> {
                    if (!produtoExistente.getId().equals(id)) {
                        throw new ProdutoDuplicadoException("O nome '" + produtoRequest.getNome() + "' já está em uso por outro produto.");
                    }
                });

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