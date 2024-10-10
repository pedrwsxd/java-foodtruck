package br.projeto.spring.pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.projeto.spring.produto.Produto;
import br.projeto.spring.repository.ProdutoRepository;
import br.projeto.spring.repository.UsuarioRepository;
import br.projeto.spring.usuario.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Entity(name = "Pedido")
@Table(name = "pedidos")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String status;
	
	private LocalDateTime dataPedido;
	
	@ManyToOne
	private Usuario cliente;
	
	@ManyToMany
	private List<Produto> produtos = new ArrayList<>();
	
	private BigDecimal total;
	

	@Autowired
	private ProdutoRepository produtoRepository;
	private UsuarioRepository usuarioRepository;
	
	public Pedido(Usuario cliente, List<Produto> produtos) {
		this.cliente = cliente;
		this.produtos = produtos;
		this.setStatus("Em preparo");
		this.setDataPedido(LocalDateTime.now());
		this.setTotal(calcularTotal());
	}
	
	public BigDecimal calcularTotal() {
		return produtos.stream().map(Produto::getPreco).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(LocalDateTime dataPedido) {
		this.dataPedido = dataPedido;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	
	public void atualizarInformacoes(@Valid DadosAtualizacaoPedido dados) {
		  if (dados.status() != null) {
		        this.status = dados.status();
		    }
		    if (dados.dataPedido() != null) {
		        this.dataPedido = dados.dataPedido();
		    }
		    if (dados.cliente() != null) {
		     this.cliente = usuarioRepository.findById(dados.cliente())
		      .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
		    }
		    if (dados.produtos() != null && !dados.produtos().isEmpty()) {
		        // Caso queira atualizar a lista de produtos
		        List<Produto> novosProdutos = new ArrayList<>();
		        for (Long produtoId : dados.produtos()) {
		            Produto produto = produtoRepository.findById(produtoId)
		                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
		            novosProdutos.add(produto);
		        }
		        this.produtos = novosProdutos;
		        this.total = calcularTotal(); // Atualiza o total com os novos produtos
		    }
	}

}


