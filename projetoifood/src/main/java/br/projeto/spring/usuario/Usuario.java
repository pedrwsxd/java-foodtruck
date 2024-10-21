package br.projeto.spring.usuario;

import br.projeto.spring.endereco.Endereco;
import jakarta.persistence.Embedded;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "Usuario")
@Table(name = "usuarios")
public class Usuario {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String nome;
	private String email;
	private String senha;
	private String role;
	
    @Embedded
    private Endereco endereco;
	
	public Usuario(DadosCadastroUsuario usuario) {
		this.setNome(usuario.nome());
		this.setEmail(usuario.email());
		this.setSenha(usuario.senha());
		this.setRole(usuario.role());
		this.endereco = new Endereco(usuario.endereco());
	}

	public void atualizarInformacoes(@Valid DadosAtualizacaoUsuario dados) {
		if(dados.nome() != null) {
			this.setNome(dados.nome());
		}
		if(dados.email() != null) {
			this.setEmail(dados.email());
		}
		if(dados.senha() != null) {
			this.setSenha(dados.senha());
		}
		if(dados.role() != null) {
			this.setRole(dados.role());
		}
		if(dados.endereco() != null) {
			this.endereco.atualizarInformacoes(dados.endereco());
		}
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}
	
	public Endereco getEndereco() {
		
		return endereco;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getId() {
		return id;
	}
}
