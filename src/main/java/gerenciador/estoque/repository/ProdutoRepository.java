package gerenciador.estoque.repository;


import gerenciador.estoque.model.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {
    // Finds all products whose name contains the given string, ignoring case.
    List<ProdutoEntity> findByNomeContainingIgnoreCase(String nome);
}