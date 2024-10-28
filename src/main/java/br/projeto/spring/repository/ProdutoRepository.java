package br.projeto.spring.repository;

import br.projeto.spring.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;


@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Long> findAllByAtivoTrue();
    List<Produto> findByAtivoTrue();
}
