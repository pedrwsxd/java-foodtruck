package br.projeto.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.projeto.spring.pedido.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{

}
