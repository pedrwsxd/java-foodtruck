package br.projeto.spring.controller;

import br.projeto.spring.pedido.Pedido;
import br.projeto.spring.produto.Produto;
import br.projeto.spring.pedido.DadosCadastroPedido;
import br.projeto.spring.pedido.DadosAtualizacaoPedido;
import br.projeto.spring.repository.PedidoRepository;
import br.projeto.spring.repository.ProdutoRepository;
import br.projeto.spring.repository.UsuarioRepository;
import br.projeto.spring.usuario.Usuario;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ProdutoRepository produtoRepository;


    @PostMapping
    public ResponseEntity<Pedido> cadastrarPedido(@RequestBody DadosCadastroPedido dados) {
        Usuario cliente = usuarioRepository.findById(dados.cliente())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        List<Produto> produtos = produtoRepository.findAllById(dados.produtos());

        // Verifica se a lista de produtos não está vazia
        if (produtos.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        Pedido pedido = new Pedido(cliente, produtos);
        pedidoRepository.save(pedido);
        return ResponseEntity.ok(pedido);
    }


    @GetMapping("/listar")
    public ResponseEntity<List<Pedido>> listarPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obterPedido(@PathVariable Long id) {
        return pedidoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizarPedido(@PathVariable Long id, @RequestBody DadosAtualizacaoPedido dados) {
        return pedidoRepository.findById(id)
                .map(pedido -> {
                    pedido.atualizarInformacoes(dados);
                    pedidoRepository.save(pedido);
                    return ResponseEntity.ok(pedido);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}