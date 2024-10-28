package br.projeto.spring.pedido;

import br.projeto.spring.produto.Produto;
import br.projeto.spring.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

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

    @NotBlank
    private String status;

    @NotNull
    private LocalDateTime dataPedido;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Usuario cliente;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "pedidos_produtos",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "produtos_id")
    )
    private List<Produto> produtos;

    @NotNull
    @PositiveOrZero
    private BigDecimal total;

    public Pedido(
            Long id,
            String status,
            LocalDateTime dataPedido,
            Usuario cliente,
            List<Produto> produtos,
            BigDecimal total
    ) {

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
        this.produtos = new ArrayList<>();
    }

    // Método para atualizar informações
    public void atualizarInformacoes(@Valid DadosAtualizacaoPedido dadosAtualizacaoPedido) {
        if (dadosAtualizacaoPedido.status() != null) {
            this.setStatus(dadosAtualizacaoPedido.status());
        }
        if (dadosAtualizacaoPedido.produtos() != null) {
            this.produtos = dadosAtualizacaoPedido.produtos();
            this.setTotal(calcularTotal());
        }
        if (dadosAtualizacaoPedido.dataPedido() != null) {
            this.setDataPedido(dadosAtualizacaoPedido.dataPedido());
        }
    }



    public BigDecimal calcularTotal() {
        return this.produtos.stream()
                .map(Produto::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void setStatus(String status) {
        this.status = status;
    }

    private void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    private void setTotal(BigDecimal total) {
        this.total = total;
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