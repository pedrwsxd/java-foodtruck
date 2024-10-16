package br.projeto.spring.produto;

import java.math.BigDecimal;

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
@Table(name = "produtos")
@Entity(name = "Produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private BigDecimal preco;

    private String tipo;

	private String sabor;

    // Campo para indicar se o produto está ativo ou não
    private boolean ativo;

    public Produto(DadosCadastroProduto produto) {
        this.setNome(produto.nome());
        this.setPreco(produto.preco());
        this.setTipo(produto.tipo());
        this.setSabor(produto.sabor());
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoProduto dados) {
        if (dados.nome() != null) {
            this.setNome(dados.nome());
        }
        if (dados.preco() != null) {
            this.setPreco(dados.preco());
        }
        if (dados.tipo() != null) {
            this.setTipo(dados.tipo());
        }
        if (dados.sabor() != null) {
            this.setSabor(dados.sabor());
        }
    }

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}