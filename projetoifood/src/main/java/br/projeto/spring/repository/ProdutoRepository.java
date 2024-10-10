package br.projeto.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.projeto.spring.produto.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}