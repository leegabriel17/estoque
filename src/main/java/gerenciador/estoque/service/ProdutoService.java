package gerenciador.estoque.service;

import gerenciador.estoque.converter.ProdutoConverter;
import gerenciador.estoque.model.ProdutoEntity;
import gerenciador.estoque.repository.ProdutoRepository;
import gerenciador.estoque.request.ProdutoRequest;
import gerenciador.estoque.response.ProdutoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
            produtoEntity.setNome(produtoEntity.getNome());
            produtoEntity.setTipo(produtoEntity.getTipo());
            produtoEntity.setPreco(produtoEntity.getPreco());
            produtoEntity.setQuantidade(produtoEntity.getQuantidade());
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