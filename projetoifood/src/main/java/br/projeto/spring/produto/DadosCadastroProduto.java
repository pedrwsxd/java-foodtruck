package br.projeto.spring.produto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroProduto(
		
		@NotBlank
		String nome,
		
		@NotBlank
		BigDecimal preco,
		
		@NotBlank
		String tipo,
		
		@NotBlank
		String sabor
		
		) {

}
