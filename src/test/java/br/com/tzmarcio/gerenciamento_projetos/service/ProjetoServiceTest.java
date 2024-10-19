package br.com.tzmarcio.gerenciamento_projetos.service;

import br.com.tzmarcio.gerenciamento_projetos.enums.Status;
import br.com.tzmarcio.gerenciamento_projetos.model.Projeto;
import br.com.tzmarcio.gerenciamento_projetos.repository.ProjetoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProjetoServiceTest {

    @Mock
    private ProjetoRepository repository;

    @InjectMocks
    private ProjetoService service;

    @Test
    public void testCriarProjeto() {
        Projeto projeto = new Projeto();
        projeto.setNome("Novo Projeto");

        when(repository.save(projeto)).thenReturn(projeto);

        Projeto criado = service.salvar(projeto);
        assertEquals("Novo Projeto", criado.getNome());
    }

    @Test
    public void testBuscarProjetoPorId() {
        Projeto projeto = new Projeto();
        projeto.setId(1L);
        projeto.setNome("Projeto Existente");

        when(repository.findById(1L)).thenReturn(Optional.of(projeto));

        Optional<Projeto> resultado = service.buscarPorId(1L);
        assertTrue(resultado.isPresent());
        assertEquals("Projeto Existente", resultado.get().getNome());
    }

    @Test
    public void naoDeveExcluirProjetoIniciado() {
        final Projeto projeto = new Projeto();
        projeto.setId(1L);
        projeto.setNome("Projeto");
        projeto.setStatus(Status.INICIADO);

        when(repository.findById(1L)).thenReturn(Optional.of(projeto));

        Assertions.assertThrows(Exception.class, () -> this.service.deletar(projeto.getId()));
    }

    @Test
    public void naoDeveExcluirProjetoEmAndamento() {
        final Projeto projeto = new Projeto();
        projeto.setId(1L);
        projeto.setNome("Projeto");
        projeto.setStatus(Status.EM_ANDAMENTO);

        when(repository.findById(1L)).thenReturn(Optional.of(projeto));

        Assertions.assertThrows(Exception.class, () -> this.service.deletar(projeto.getId()));
    }

    @Test
    public void naoDeveExcluirProjetoEncerrado() {
        final Projeto projeto = new Projeto();
        projeto.setId(1L);
        projeto.setNome("Projeto");
        projeto.setStatus(Status.ENCERRADO);

        when(repository.findById(1L)).thenReturn(Optional.of(projeto));

        Assertions.assertThrows(Exception.class, () -> this.service.deletar(projeto.getId()));
    }

    @Test
    public void testExclusaoProjeto() {
        final Projeto projeto = new Projeto();
        projeto.setId(1L);
        projeto.setNome("Projeto");
        projeto.setStatus(Status.EM_ANALISE);

        when(repository.findById(1L)).thenReturn(Optional.of(projeto));

        Assertions.assertDoesNotThrow(() -> this.service.deletar(projeto.getId()));
    }

}
