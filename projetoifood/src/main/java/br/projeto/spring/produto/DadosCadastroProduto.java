package br.projeto.spring.produto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroProduto(
		
		@NotBlank
		String nome,
		
		@NotNull
		BigDecimal preco,
		
		@NotBlank
		String tipo,
		
		@NotBlank
		String sabor
		
		) {

}
