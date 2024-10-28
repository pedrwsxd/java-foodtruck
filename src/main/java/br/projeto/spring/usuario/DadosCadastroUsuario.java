package br.projeto.spring.usuario;

import br.projeto.spring.endereco.DadosEndereco;
import br.projeto.spring.roles.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record DadosCadastroUsuario(

		@NotBlank
		String nome,

		@NotBlank
		String telefone,

		@NotBlank
		String email,

		@NotBlank
		String senha,

		@NotBlank
		Set<Role> roles

) {

}
