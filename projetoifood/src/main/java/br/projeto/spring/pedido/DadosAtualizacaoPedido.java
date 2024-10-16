package br.projeto.spring.pedido;

import br.projeto.spring.produto.Produto;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record DadosAtualizacaoPedido(

        @NotNull
    Long id, // ID do pedido a ser atualizado

        String status, // Status do pedido
        LocalDateTime dataPedido, // Data do pedido
        Long cliente, // ID do cliente
        List<Produto> produtos // Lista de IDs dos produtos
    ) {
}
