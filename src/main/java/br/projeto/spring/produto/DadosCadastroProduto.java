package br.projeto.spring.produto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.CrossOrigin;


public record DadosCadastroProduto(
		
		@NotBlank
		String nome,
		
		@NotBlank
		String imagemUrl,
		
		@NotNull
		BigDecimal preco,
		
		@NotBlank
		String tipo,
		
		@NotBlank
		String sabor
		
		) {

}
