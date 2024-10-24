package br.projeto.spring.usuario;

import br.projeto.spring.endereco.Endereco;
import br.projeto.spring.roles.Role;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

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

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "usuarios_roles",
			joinColumns = @JoinColumn(name = "usuario_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;  // Papéis do usuário

	private String nome;
	private String email;
	private String senha;

	// Construtor baseado nos dados do DTO DadosCadastroUsuario
	public Usuario(DadosCadastroUsuario usuario) {
		this.setNome(usuario.nome());
		this.setEmail(usuario.email());
		this.setSenha(usuario.senha());
	}

	// Método para atualizar as informações do usuário
	public void atualizarInformacoes(@Valid DadosAtualizacaoUsuario dados) {
		if (dados.nome() != null) {
			this.setNome(dados.nome());
		}
		if (dados.email() != null) {
			this.setEmail(dados.email());
		}
		if (dados.senha() != null) {
			this.setSenha(dados.senha());
		}
		if (dados.roles() != null && !dados.roles().isEmpty()) {
			this.setRoles(dados.roles());
		}
	}

	// Getters e Setters (esses podem ser omitidos se você estiver usando Lombok corretamente)
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

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
