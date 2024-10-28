package br.projeto.spring.produto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.CrossOrigin;


public record DadosAtualizacaoProduto(
		@NotBlank
		@Size(max = 255)
		String nome,

		@Size(max = 255)
		String imagemUrl,

		@PositiveOrZero
		BigDecimal preco,

		@Size(max = 255)
		String tipo,

		@Size(max = 255)
		String sabor
		
		) {

}
