package gerenciador.estoque.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String tipo;
    private int quantidade;
    private BigDecimal preco;
}
