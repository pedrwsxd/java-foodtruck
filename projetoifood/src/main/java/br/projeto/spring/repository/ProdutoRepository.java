package br.projeto.spring.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.projeto.spring.produto.Produto;

import java.util.List;


@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

    // Busca todos os produtos que estão ativos
    List<Long> findAllByAtivoTrue();

}
