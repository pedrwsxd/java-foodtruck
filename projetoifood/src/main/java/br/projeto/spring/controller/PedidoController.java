package br.projeto.spring.controller;

import br.projeto.spring.pedido.DadosAtualizacaoPedido;
import br.projeto.spring.pedido.DadosCadastroPedido;
import br.projeto.spring.pedido.Pedido;
import br.projeto.spring.repository.PedidoRepository;
import br.projeto.spring.repository.ProdutoRepository;
import br.projeto.spring.repository.UsuarioRepository;
import br.projeto.spring.usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoController(PedidoRepository pedidoRepository, UsuarioRepository usuarioRepository, ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.produtoRepository = produtoRepository;
    }

    // Criar um novo pedido (POST)
    @PostMapping("/novopedido")
    public ResponseEntity<Pedido> criarPedido(@RequestBody @Valid DadosCadastroPedido dados) {
        var cliente = usuarioRepository.findById(dados.cliente())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));

        // Certifique-se de que está passando uma lista de IDs de produtos (List<Long>)
        var produtos = produtoRepository.findAllById(dados.produtos());

        if (produtos.isEmpty()) {
            return ResponseEntity.badRequest().body(null);  // Verifica se a lista de produtos está vazia
        }

        Pedido novoPedido = new Pedido(cliente, produtos);
        pedidoRepository.save(novoPedido);

        return ResponseEntity.ok(novoPedido);
    }

    // Buscar um pedido por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPedido(@PathVariable Long id) {
        var pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado!"));

        return ResponseEntity.ok(pedido);
    }

    // Atualizar um pedido (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizarPedido(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoPedido dados) {
        var pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado!"));

        pedido.atualizarInformacoes(dados);
        pedidoRepository.save(pedido);

        return ResponseEntity.ok(pedido);
    }

    // Excluir um pedido (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        var pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado!"));

        pedidoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
