package br.projeto.spring.usuario;

public record DadosAtualizacaoUsuario(
		
		Long id,
		
		String nome,
		
		String email,
		
		String senha,
		
		String role 
		) {

}
