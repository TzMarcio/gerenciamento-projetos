package br.com.tzmarcio.gerenciamento_projetos.repository;

import br.com.tzmarcio.gerenciamento_projetos.model.Pessoa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PessoaRepositoryTest {

    @Autowired
    private PessoaRepository pessoaRepository;

    @BeforeEach
    void setUp() {
        final Pessoa gerente = new Pessoa();
        gerente.setNome("João Gerente");
        gerente.setGerente(true);
        gerente.setFuncionario(false);
        pessoaRepository.save(gerente);

        final Pessoa funcionario = new Pessoa();
        funcionario.setNome("Ana Funcionária");
        funcionario.setFuncionario(true);
        funcionario.setGerente(false);
        pessoaRepository.save(funcionario);
    }

    @Test
    void testFindByNomeContainingIgnoreCaseAndGerenteIsTrue() {
        List<Pessoa> result = pessoaRepository.findByNomeContainingIgnoreCaseAndGerenteIsTrue("joão");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNome()).isEqualTo("João Gerente");
    }

    @Test
    void testFindByNomeContainingIgnoreCaseAndFuncionarioIsTrue() {
        List<Pessoa> result = pessoaRepository.findByNomeContainingIgnoreCaseAndFuncionarioIsTrue("ana");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNome()).isEqualTo("Ana Funcionária");
    }
}