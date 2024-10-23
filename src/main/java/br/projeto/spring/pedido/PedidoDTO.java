package br.projeto.spring.pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoDTO(
        Long id,
        String status,
        LocalDateTime dataPedido,
        Long cliente, // ID do cliente sem a senha
        List<Long> produtosIds, // IDs dos produtos
        BigDecimal total
) {
}
