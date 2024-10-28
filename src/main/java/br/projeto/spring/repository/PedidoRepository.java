package br.projeto.spring.repository;

import br.projeto.spring.pedido.Pedido;
import br.projeto.spring.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByCliente(Usuario usuario);
}
