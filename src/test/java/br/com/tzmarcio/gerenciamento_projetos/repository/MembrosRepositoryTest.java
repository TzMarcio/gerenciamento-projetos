package br.com.tzmarcio.gerenciamento_projetos.repository;

import br.com.tzmarcio.gerenciamento_projetos.model.Membros;
import br.com.tzmarcio.gerenciamento_projetos.model.Projeto;
import br.com.tzmarcio.gerenciamento_projetos.model.Pessoa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MembrosRepositoryTest {

    @Autowired
    private MembrosRepository membrosRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    private Projeto projeto;
    private Pessoa pessoa;

    @BeforeEach
    public void setup() {
        final Pessoa gerente = pessoaRepository.save(Pessoa.builder().nome("Miguel Costa").gerente(true).build());
        this.projeto = projetoRepository.save(Projeto.builder().nome("Projeto 1").gerente(gerente).build());
        this.pessoa = pessoaRepository.save(Pessoa.builder().nome("João Silva").funcionario(true).build());
    }

    @Test
    void testFindByProjetoId() {
        Membros membro = new Membros();
        membro.setProjeto(projeto);
        membro.setPessoa(pessoa);
        membrosRepository.save(membro);

        List<Membros> membrosList = membrosRepository.findByProjetoId(projeto.getId());

        assertThat(membrosList).hasSize(1);
        assertThat(membrosList.get(0).getPessoa().getNome()).isEqualTo("João Silva");
    }

    @Test
    void testDeleteAllByProjetoId() {
        Membros membro = new Membros();
        membro.setProjeto(projeto);
        membro.setPessoa(pessoa);
        membrosRepository.save(membro);

        assertThat(membrosRepository.findByProjetoId(projeto.getId())).hasSize(1);

        membrosRepository.deleteAllByProjetoId(projeto.getId());

        assertThat(membrosRepository.findByProjetoId(projeto.getId())).isEmpty();
    }
}