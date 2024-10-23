package br.projeto.spring.produto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoProduto(
		
		String nome,
		
		String imagemUrl, // URL da imagem do produto
		
		BigDecimal preco,
		
		String tipo,
		
		String sabor
		
		) {

}
