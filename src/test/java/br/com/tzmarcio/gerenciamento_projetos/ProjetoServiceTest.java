package br.com.tzmarcio.gerenciamento_projetos;

import br.com.tzmarcio.gerenciamento_projetos.enums.Status;
import br.com.tzmarcio.gerenciamento_projetos.model.Projeto;
import br.com.tzmarcio.gerenciamento_projetos.service.ProjetoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProjetoServiceTest {

    @Autowired
    private ProjetoService service;

    @Test
    public void naoDeveExcluirProjetoIniciado() {
        final Projeto projeto = new Projeto();
        projeto.setStatus(Status.INICIADO);

        Assertions.assertThrows(Exception.class, () -> this.service.deletar(projeto.getId()));
    }

}
