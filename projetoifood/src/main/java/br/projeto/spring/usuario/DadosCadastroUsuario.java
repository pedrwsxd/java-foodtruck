package br.projeto.spring.usuario;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroUsuario(
		
		 @NotBlank		
		 String nome,
         
		 @NotBlank
	     String email,
         
	     @NotBlank
		 String senha,
		 
		 @NotBlank
		 String role
		
		
		) {

	
	
}
