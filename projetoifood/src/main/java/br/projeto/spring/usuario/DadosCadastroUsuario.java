package br.projeto.spring.usuario;



import br.projeto.spring.endereco.DadosEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroUsuario(

		@NotBlank 
		String nome,

		@NotBlank 
		String email,

		@NotBlank 
		String senha,

		@NotBlank 
		String role,

		@NotNull @Valid 
		DadosEndereco endereco

) {

}
