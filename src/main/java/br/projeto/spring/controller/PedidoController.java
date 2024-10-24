package br.projeto.spring.controller;

import br.projeto.spring.pedido.DadosAtualizacaoPedido;
import br.projeto.spring.pedido.DadosCadastroPedido;
import br.projeto.spring.pedido.Pedido;
import br.projeto.spring.pedido.PedidoDTO;
import br.projeto.spring.produto.Produto;
import br.projeto.spring.repository.PedidoRepository;
import br.projeto.spring.repository.ProdutoRepository;
import br.projeto.spring.repository.UsuarioRepository;
import br.projeto.spring.usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<PedidoDTO> criarPedido(@RequestBody @Valid DadosCadastroPedido dados) {
        var cliente = usuarioRepository.findById(dados.cliente())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));

        var produtos = produtoRepository.findAllById(dados.produtos());

        if (produtos.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        Pedido novoPedido = new Pedido(cliente, produtos);
        pedidoRepository.save(novoPedido);

        PedidoDTO pedidoDTO = new PedidoDTO(
                novoPedido.getId(),
                novoPedido.getStatus(),
                novoPedido.getDataPedido(),
                novoPedido.getCliente().getId(),
                novoPedido.getProdutos().stream().map(produto -> produto.getId()).collect(Collectors.toList()),
                novoPedido.getTotal()
        );

        return ResponseEntity.ok(pedidoDTO);
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
