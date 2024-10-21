package br.com.tzmarcio.gerenciamento_projetos.service;

import br.com.tzmarcio.gerenciamento_projetos.enums.Status;
import br.com.tzmarcio.gerenciamento_projetos.model.Pessoa;
import br.com.tzmarcio.gerenciamento_projetos.model.Projeto;
import br.com.tzmarcio.gerenciamento_projetos.repository.MembrosRepository;
import br.com.tzmarcio.gerenciamento_projetos.repository.ProjetoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.naming.NoPermissionException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProjetoServiceTest {

    @Mock
    private ProjetoRepository repository;

    @Mock
    private MembrosRepository membrosRepository;

    @Mock
    private PessoaService pessoaService;

    @InjectMocks
    private ProjetoService service;

    private Projeto projeto;
    private Pessoa gerente;

    @BeforeEach
    void setUp() {
        projeto = new Projeto();
        projeto.setNome("Novo Projeto");
        projeto.setGerenteId(1L);

        gerente = new Pessoa();
        gerente.setId(1L);
        gerente.setNome("Gerente Teste");
    }

    @Test
    void testCriarProjeto() {
        when(membrosRepository.findByProjetoId(1L)).thenReturn(Collections.emptyList());
        when(pessoaService.buscarPorId(1L)).thenReturn(Optional.of(gerente));
        when(repository.save(projeto)).thenReturn(projeto);

        final Projeto criado = service.salvar(projeto);
        assertEquals("Novo Projeto", criado.getNome());
        verify(repository, times(1)).save(projeto);  // Verifica se o método save foi chamado uma vez
    }

    @Test
    void testSalvarProjetoSemGerente() {
        projeto.setGerenteId(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.salvar(projeto));
        assertEquals("É obrigatório selecionar um gerente.", exception.getMessage());
    }

    @Test
    void testSalvarProjetoGerenteNaoEncontrado() {
        when(pessoaService.buscarPorId(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.salvar(projeto));
        assertEquals("Gerente não encontrado", exception.getMessage());
    }

    @Test
    void testSalvarProjetoComMembros() {
        projeto.setId(1L);
        projeto.setPessoas(List.of(1L, 2L));

        Pessoa membro1 = new Pessoa();
        membro1.setId(1L);
        membro1.setNome("Membro 1");

        Pessoa membro2 = new Pessoa();
        membro2.setId(2L);
        membro2.setNome("Membro 2");

        when(pessoaService.buscarPorId(1L)).thenReturn(Optional.of(gerente));
        when(repository.save(projeto)).thenReturn(projeto);
        when(pessoaService.buscarPorIds(projeto.getPessoas())).thenReturn(List.of(membro1, membro2));

        final Projeto salvo = service.salvar(projeto);
        assertEquals("Novo Projeto", salvo.getNome());
        verify(membrosRepository, times(1)).deleteAllByProjetoId(projeto.getId());
        verify(membrosRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testBuscarProjetoPorId() {
        projeto.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(projeto));

        final Projeto resultado = service.buscarPorId(1L);
        assertTrue(Objects.nonNull(resultado));
        assertEquals("Novo Projeto", resultado.getNome());
    }

    @Test
    void naoDeveExcluirProjetoIniciado() {
        projeto.setId(1L);
        projeto.setStatus(Status.INICIADO);

        when(repository.findById(1L)).thenReturn(Optional.of(projeto));

        Assertions.assertThrows(NoPermissionException.class, () -> service.deletar(projeto.getId()));
    }

    @Test
    void naoDeveExcluirProjetoEmAndamento() {
        projeto.setId(1L);
        projeto.setStatus(Status.EM_ANDAMENTO);

        when(repository.findById(1L)).thenReturn(Optional.of(projeto));

        Assertions.assertThrows(NoPermissionException.class, () -> service.deletar(projeto.getId()));
    }

    @Test
    void naoDeveExcluirProjetoEncerrado() {
        projeto.setId(1L);
        projeto.setStatus(Status.ENCERRADO);

        when(repository.findById(1L)).thenReturn(Optional.of(projeto));

        Assertions.assertThrows(NoPermissionException.class, () -> service.deletar(projeto.getId()));
    }

    @Test
    void testExclusaoProjeto() {
        projeto.setId(1L);
        projeto.setStatus(Status.EM_ANALISE);

        when(repository.findById(1L)).thenReturn(Optional.of(projeto));

        Assertions.assertDoesNotThrow(() -> service.deletar(projeto.getId()));
        verify(repository, times(1)).delete(projeto);  // Verifica se o método delete foi chamado uma vez
    }
}
