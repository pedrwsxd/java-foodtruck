package br.projeto.spring.pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.projeto.spring.produto.Produto;
import br.projeto.spring.usuario.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
	
	public Pedido() {}
	
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
	
	
}
