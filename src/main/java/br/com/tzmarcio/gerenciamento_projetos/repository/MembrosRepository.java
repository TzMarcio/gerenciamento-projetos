package br.com.tzmarcio.gerenciamento_projetos.repository;

import br.com.tzmarcio.gerenciamento_projetos.model.Membros;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembrosRepository extends JpaRepository<Membros, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Membros m WHERE m.projeto.id = ?1")
    void deleteAllByProjetoId(final Long id);

    List<Membros> findByProjetoId(final Long id);

}
