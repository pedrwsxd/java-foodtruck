package br.projeto.spring.pedido;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DadosCadastroPedido(

        @NotNull
        Long cliente,

        @NotEmpty
        List<Long> produtos
) {
}
