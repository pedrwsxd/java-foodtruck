package br.projeto.spring.usuario;

import br.projeto.spring.endereco.DadosEndereco;

public record DadosAtualizacaoUsuario(

		Long id,

		String nome,

		String email,

		String senha,

		String role,

		DadosEndereco endereco
) {

}
