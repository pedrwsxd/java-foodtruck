package br.projeto.spring.produto;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.CrossOrigin;


@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "produtos")
@Entity(name = "Produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String nome;

    @Size(max = 255)
	private String imagemUrl;

    @NotNull
    @PositiveOrZero
	private BigDecimal preco;

    @Size(max = 255)
    private String tipo;

    @Size(max = 255)
	private String sabor;

    private boolean ativo = true;

    public Produto(DadosCadastroProduto produto) {
        this.setNome(produto.nome());
        this.setPreco(produto.preco());
        this.setTipo(produto.tipo());
        this.setSabor(produto.sabor());
        this.setImagemUrl(produto.imagemUrl());
    }

    public Produto() {
    	
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
        if (dados.imagemUrl() != null) {
            this.setImagemUrl(dados.imagemUrl());
        }
    }
    
    public Long getId(){
    	return id;
    }

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	
	public String getImagemUrl() {
		return imagemUrl;
	}
	
	public void setImagemUrl( String string) {
		this.imagemUrl = string;
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