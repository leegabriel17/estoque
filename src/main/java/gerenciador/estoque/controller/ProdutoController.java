package gerenciador.estoque.controller;

import gerenciador.estoque.request.ProdutoRequest;
import gerenciador.estoque.response.ProdutoResponse;
import gerenciador.estoque.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@Tag(name = "Produtos", description = "Endpoints para Gerenciamento de Produtos")
@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @Operation(summary = "Cria um novo produto", description = "Registra um novo produto no estoque e retorna os dados do produto criado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping
    public ResponseEntity<ProdutoResponse> salvar(@RequestBody @Valid ProdutoRequest produtoRequest, UriComponentsBuilder uriBuilder) {
        log.info("Recebida requisição para criar novo produto: {}", produtoRequest.getNome());
        ProdutoResponse produtoSalvo = produtoService.salvar(produtoRequest);
        URI uri = uriBuilder.path("/produtos/{id}").buildAndExpand(produtoSalvo.getId()).toUri();
        return ResponseEntity.created(uri).body(produtoSalvo);
    }

    @Operation(summary = "Atualiza um produto existente", description = "Atualiza os dados de um produto com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(@PathVariable Long id, @RequestBody @Valid ProdutoRequest produtoRequest) {
        log.info("Recebida requisição para atualizar produto com ID: {}", id);
        ProdutoResponse produtoAtualizado = produtoService.atualizar(id, produtoRequest);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @Operation(summary = "Deleta um produto", description = "Remove um produto do estoque com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        log.info("Recebida requisição para deletar produto com ID: {}", id);
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Lista todos os produtos", description = "Retorna uma lista com todos os produtos cadastrados no estoque.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso")})
    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> listarTodos() {
        log.info("Recebida requisição para listar todos os produtos.");
        return ResponseEntity.ok(produtoService.listarTodos());
    }

    @Operation(summary = "Busca um produto por ID", description = "Retorna os dados de um produto específico com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscarPorId(@PathVariable Long id) {
        log.info("Recebida requisição para buscar produto por ID: {}", id);
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }
}