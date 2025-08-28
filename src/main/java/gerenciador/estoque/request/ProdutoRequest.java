package gerenciador.estoque.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProdutoRequest {
    private Long id;

    @NotBlank(message = "O nome do produto não pode ser vazio.")
    @Size(min = 3, max = 100, message = "O nome do produto deve ter entre 3 e 100 caracteres.")
    private String nome;

    @NotBlank(message = "O tipo do produto não pode ser vazio.")
    private String tipo;

    @Positive(message = "A quantidade deve ser um número positivo.")
    private int quantidade;

    @NotNull(message = "O preço não pode ser nulo.")
    @Positive(message = "O preço deve ser um valor positivo.")
    private BigDecimal preco;
}
