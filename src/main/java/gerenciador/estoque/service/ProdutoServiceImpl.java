package gerenciador.estoque.service;

import gerenciador.estoque.converter.ProdutoConverter;
import gerenciador.estoque.exception.ProdutoDuplicadoException;
import gerenciador.estoque.exception.ProdutoNaoEncontradoException;
import gerenciador.estoque.model.ProdutoEntity;
import gerenciador.estoque.repository.ProdutoRepository;
import gerenciador.estoque.request.ProdutoRequest;
import gerenciador.estoque.response.ProdutoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoServiceInterface{

    @Autowired
    private final ProdutoRepository produtoRepository;

    public ProdutoResponse salvar(ProdutoRequest produtoRequest) {
        log.info("Iniciando processo para salvar novo produto com nome: {}", produtoRequest.getNome());
        if (produtoRepository.existsByNomeIgnoreCase(produtoRequest.getNome())) {
            log.warn("Tentativa de salvar produto com nome duplicado: {}", produtoRequest.getNome());
            throw new ProdutoDuplicadoException("Já existe um produto cadastrado com o nome: " + produtoRequest.getNome());
        }
        ProdutoEntity produtoSalvo = produtoRepository.save(ProdutoConverter.toEntity(produtoRequest));
        log.info("Produto salvo com sucesso com ID: {}", produtoSalvo.getId());
        return ProdutoConverter.toResponse(produtoSalvo);
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
        log.info("Iniciando processo para deletar produto com ID: {}", id);
        if (!produtoRepository.existsById(id)) {
            log.warn("Tentativa de deletar produto não encontrado com ID: {}", id);
            throw new ProdutoNaoEncontradoException("Produto com ID " + id + " não encontrado para exclusão.");
        }
        produtoRepository.deleteById(id);
        log.info("Produto com ID {} deletado com sucesso.", id);
    }

    public List<ProdutoResponse> listarTodos() {
        log.info("Buscando todos os produtos no repositório.");
        List<ProdutoResponse> produtos = produtoRepository.findAll().stream()
                .map(ProdutoConverter::toResponse)
                .toList();
        log.info("Encontrados {} produtos.", produtos.size());
        return produtos;
    }

    public ProdutoResponse buscarPorId(Long id) {
        log.info("Buscando produto por ID: {}", id);
        return produtoRepository.findById(id)
                .map(ProdutoConverter::toResponse)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto com ID " + id + " não encontrado."));
    }
}