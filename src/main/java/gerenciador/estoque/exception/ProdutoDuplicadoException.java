package gerenciador.estoque.exception;

public class ProdutoDuplicadoException extends RuntimeException {
    public ProdutoDuplicadoException(String message) {
        super(message);
    }
}