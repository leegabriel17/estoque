package gerenciador.estoque.repository;


import gerenciador.estoque.model.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {

    boolean existsByNomeIgnoreCase(String nome);

    Optional<ProdutoEntity> findByNomeIgnoreCase(String nome);
}