package br.projeto.spring.usuario;

import br.projeto.spring.endereco.DadosEndereco;
import br.projeto.spring.roles.Role;

import java.util.Set;

public record DadosAtualizacaoUsuario(

		Long id,

		String nome,

		String email,

		String senha,

		Set<Role> roles
) {

}
