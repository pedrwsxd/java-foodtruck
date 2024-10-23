package br.projeto.spring.pedido;

import br.projeto.spring.produto.Produto;
import br.projeto.spring.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List; 


@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private LocalDateTime dataPedido;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Usuario cliente;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Produto> produtos;

    private BigDecimal total;

    public Pedido(Long id, String status, LocalDateTime dataPedido, Usuario cliente, List<Produto> produtos,
                   BigDecimal total) {
        super();
        this.id = id;
        this.status = status;
        this.dataPedido = dataPedido;
        this.cliente = cliente;
        this.produtos = produtos;
        this.total = total;
    }

    // Construtor
    public Pedido(Usuario cliente, List<Produto> produtos) {
        this.cliente = cliente;
        this.produtos = produtos;
        this.setStatus("Em preparo");
        this.setDataPedido(LocalDateTime.now());
        this.setTotal(this.calcularTotal());
    }

    public Pedido() {
        this.produtos = new ArrayList<>(); // Inicializa a lista de produtos
    }

    // Método para atualizar informações
    public void atualizarInformacoes(@Valid DadosAtualizacaoPedido dadosAtualizacaoPedido) {
        if (dadosAtualizacaoPedido.status() != null) {
            this.setStatus(dadosAtualizacaoPedido.status());
        }
        if (dadosAtualizacaoPedido.produtos() != null) {
            this.produtos = dadosAtualizacaoPedido.produtos();
            this.setTotal(calcularTotal()); // Recalcula o total quando os produtos são atualizados
        }
        if (dadosAtualizacaoPedido.dataPedido() != null) {
            this.setDataPedido(dadosAtualizacaoPedido.dataPedido());
        }
    }

    // Métodos auxiliares (getters e setters)
    private void setStatus(String status) {
        this.status = status;
    }

    private void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    private void setTotal(BigDecimal total) {
        this.total = total;
    }

    // Método para calcular o total do pedido
    public BigDecimal calcularTotal() {
        return this.produtos.stream()
                .map(Produto::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    public String getStatus() {
        return status;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getTotal(){
        return total;
    }

    public Long getId() {
        return id;
    }

    public List<Produto> getProdutos() { // Corrigido o tipo de retorno
        return produtos;
    }
}