package br.com.tzmarcio.gerenciamento_projetos.service;

import org.springframework.boot.test.context.SpringBootTest;

import br.com.tzmarcio.gerenciamento_projetos.model.Pessoa;
import br.com.tzmarcio.gerenciamento_projetos.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PessoaService pessoaService;

    private Pessoa gerente;
    private Pessoa funcionario;

    @BeforeEach
    void setUp() {

        gerente = new Pessoa();
        gerente.setId(1L);
        gerente.setNome("João Gerente");
        gerente.setGerente(true);

        funcionario = new Pessoa();
        funcionario.setId(2L);
        funcionario.setNome("Ana Funcionária");
        funcionario.setFuncionario(true);
    }

    @Test
    void testBuscarGerentes() {
        when(pessoaRepository.findByNomeContainingIgnoreCaseAndGerenteIsTrue("joão"))
                .thenReturn(Collections.singletonList(gerente));

        List<Pessoa> result = pessoaService.buscarGerentes("joão");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNome()).isEqualTo("João Gerente");
    }

    @Test
    void testBuscarFuncionarios() {
        when(pessoaRepository.findByNomeContainingIgnoreCaseAndFuncionarioIsTrue("ana"))
                .thenReturn(Collections.singletonList(funcionario));

        List<Pessoa> result = pessoaService.buscarFuncionarios("ana");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNome()).isEqualTo("Ana Funcionária");
    }

    @Test
    void testBuscarPorId() {
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(gerente));

        Optional<Pessoa> result = pessoaService.buscarPorId(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getNome()).isEqualTo("João Gerente");
    }

    @Test
    void testSalvarPessoa() {
        when(pessoaRepository.save(gerente)).thenReturn(gerente);

        Pessoa result = pessoaService.salvar(gerente);

        assertThat(result.getNome()).isEqualTo("João Gerente");
    }

    @Test
    void testBuscarPorIds() {
        when(pessoaRepository.findAllById(Arrays.asList(1L, 2L)))
                .thenReturn(Arrays.asList(gerente, funcionario));

        List<Pessoa> result = pessoaService.buscarPorIds(Arrays.asList(1L, 2L));

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getNome()).isEqualTo("João Gerente");
        assertThat(result.get(1).getNome()).isEqualTo("Ana Funcionária");
    }
}
