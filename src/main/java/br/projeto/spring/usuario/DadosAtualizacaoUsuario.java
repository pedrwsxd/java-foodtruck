package br.projeto.spring.usuario;
import br.projeto.spring.roles.Role;
import jakarta.validation.constraints.*;

import java.util.Set;

public record DadosAtualizacaoUsuario(

		@NotNull
		Long id,

		@NotBlank
		@Size(max = 255)
		String nome,

		@Size(max = 20)
		String telefone,

		@NotBlank
		@Email
		String email,

		@NotBlank
		@Size(min = 6, max = 255)
		String senha,

		@NotEmpty
		Set<Role> roles
) {

}
