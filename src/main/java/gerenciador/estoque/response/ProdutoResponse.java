package gerenciador.estoque.response;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProdutoResponse {

    private Long id;
    private String nome;
    private String tipo;
    private int quantidade;
    private BigDecimal preco;
}
