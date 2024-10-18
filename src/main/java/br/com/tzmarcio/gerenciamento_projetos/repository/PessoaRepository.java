package br.com.tzmarcio.gerenciamento_projetos.repository;

import br.com.tzmarcio.gerenciamento_projetos.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
