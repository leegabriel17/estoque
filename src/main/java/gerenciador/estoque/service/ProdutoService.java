package gerenciador.estoque.service;

import gerenciador.estoque.converter.ProdutoConverter;
import gerenciador.estoque.model.ProdutoEntity;
import gerenciador.estoque.repository.ProdutoRepository;
import gerenciador.estoque.request.ProdutoRequest;
import gerenciador.estoque.response.ProdutoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public ProdutoResponse salvar(ProdutoRequest produtoRequest) {
        return ProdutoConverter.toResponse(produtoRepository.save(ProdutoConverter.toEntity(produtoRequest)));
    }

    public ProdutoResponse atualizar(Long id, ProdutoRequest produtoRequest) {
        try {
            Optional<ProdutoEntity> produtoOpt = produtoRepository.findById(id);
            if (produtoOpt.isEmpty()) {
                throw new RuntimeException("Produto com ID " + id + " não encontrado.");
            }
            ProdutoEntity produtoEntity = produtoOpt.get();
            produtoEntity.setId(id);
            produtoEntity.setNome(produtoRequest.getNome());
            produtoEntity.setTipo(produtoRequest.getTipo());
            produtoEntity.setPreco(produtoRequest.getPreco());
            produtoEntity.setQuantidade(produtoRequest.getQuantidade());
            log.info("Produto atualizado com sucesso: {}", produtoEntity);
            return ProdutoConverter.toResponse(produtoRepository.save(produtoEntity));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar produto: " + e.getMessage(), e);
        }
    }

    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }

    public List<ProdutoResponse> listarTodos() {
        List<ProdutoEntity> ProdutoDao = produtoRepository.findAll();
        return ProdutoDao.stream()
                .map(ProdutoConverter::toResponse)
                .toList();
    }

    public ProdutoResponse buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .map(ProdutoConverter::toResponse)
                .orElse(null);
    }
}