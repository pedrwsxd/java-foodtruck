package br.projeto.spring.produto;

import java.math.BigDecimal;

import br.projeto.spring.usuario.DadosAtualizacaoUsuario;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Entity
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private BigDecimal preco;
	
	private String tipo;
	
	private String sabor;
	
	public Produto() {}
	
	public Produto(DadosCadastroProduto produto) {
		    
		this.setNome(produto.nome());
		this.setPreco(produto.preco());
		this.setTipo(produto.tipo());
		this.setSabor(produto.sabor());
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getSabor() {
		return sabor;
	}

	public void setSabor(String sabor) {
		this.sabor = sabor;
	}
	
    public void atualizarinformacoes(@Valid DadosAtualizacaoProduto dados) {
		
		if(dados.nome() != null) {
			this.nome = dados.nome();
		}
		if(dados.preco() != null) {
			this.preco = dados.preco();
		}
		if(dados.tipo() != null) {
			this.tipo = dados.tipo();
		}
		if(dados.sabor() != null) {
			this.sabor = dados.sabor();
		}
    }
}
