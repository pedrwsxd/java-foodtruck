package br.projeto.spring.pedido;

import br.projeto.spring.produto.Produto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record DadosAtualizacaoPedido(

        @NotNull
        Long id,

        String status,

        LocalDateTime dataPedido,

        Long cliente,

        @NotEmpty
        List<Produto> produtos
) {
}
