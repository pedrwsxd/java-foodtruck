package br.projeto.spring.controller;

import br.projeto.spring.pedido.DadosAtualizacaoPedido;
import br.projeto.spring.pedido.DadosCadastroPedido;
import br.projeto.spring.pedido.Pedido;
import br.projeto.spring.produto.Produto;
import br.projeto.spring.pedido.PedidoDTO;
import br.projeto.spring.repository.PedidoRepository;
import br.projeto.spring.repository.ProdutoRepository;
import br.projeto.spring.repository.UsuarioRepository;
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
                novoPedido.getCliente().getNome(),
                novoPedido.getProdutos().stream().map(Produto::getNome).collect(Collectors.toList()),
                novoPedido.getTotal()
        );

        return ResponseEntity.ok(pedidoDTO);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PedidoDTO>> listarPedidosDoUsuario(@PathVariable Long usuarioId) {
        var usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        var pedidos = pedidoRepository.findByCliente(usuario);

        var pedidosDTO = pedidos.stream()
                .map(pedido -> new PedidoDTO(
                        pedido.getId(),
                        pedido.getStatus(),
                        pedido.getDataPedido(),
                        pedido.getCliente().getNome(), // Obtém o nome do cliente
                        pedido.getProdutos().stream().map(Produto::getNome).collect(Collectors.toList()), // Obtém os nomes dos produtos
                        pedido.getTotal()
                ))
                .collect(Collectors.toList());


        return ResponseEntity.ok(pedidosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPedido(@PathVariable Long id) {
        var pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado!"));

        return ResponseEntity.ok(pedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizarPedido(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoPedido dados) {
        var pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado!"));

        pedido.atualizarInformacoes(dados);
        pedidoRepository.save(pedido);

        return ResponseEntity.ok(pedido);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        var pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado!"));

        pedidoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
