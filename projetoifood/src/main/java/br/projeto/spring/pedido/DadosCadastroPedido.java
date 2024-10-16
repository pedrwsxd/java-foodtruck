package br.projeto.spring.pedido;

import br.projeto.spring.produto.Produto;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DadosCadastroPedido(

        @NotNull
    Long cliente, // ID do cliente

        @NotNull
    List<Long> produtos // Lista de IDs dos produtos
    ) {
}
