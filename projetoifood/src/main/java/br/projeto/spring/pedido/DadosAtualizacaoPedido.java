package br.projeto.spring.pedido;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record DadosAtualizacaoPedido(
    
    @NotNull
    Long id, // ID do pedido a ser atualizado

    String status, // Status do pedido
    LocalDateTime dataPedido, // Data do pedido
    Long cliente, // ID do cliente
    List<Long> produtos // Lista de IDs dos produtos
    ) {
}
