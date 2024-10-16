package br.projeto.spring.produto;

import java.math.BigDecimal;

public record DadosAtualizacaoProduto(
		
		String nome,
		
		BigDecimal preco,
		
		String tipo,
		
		String sabor
		
		) {

}
