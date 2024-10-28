package br.projeto.spring.pedido;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoDTO(
        @NotNull
        Long id,

        @NotBlank
        String status,

        @NotNull
        LocalDateTime dataPedido,

        @NotBlank // Nome do cliente não pode ser nulo ou vazio
        String clienteNome,

        @NotEmpty // Lista de nomes de produtos não pode ser vazia
        List<String> produtosNomes,

        @NotNull
        @PositiveOrZero
        BigDecimal total
) {
}
