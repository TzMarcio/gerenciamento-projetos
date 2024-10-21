package br.com.tzmarcio.gerenciamento_projetos.controller.rest;

import br.com.tzmarcio.gerenciamento_projetos.model.Projeto;
import br.com.tzmarcio.gerenciamento_projetos.service.ProjetoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(ProjetoController.class)
class ProjetoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjetoService projetoService;

    private Projeto projeto1;
    private Projeto projeto2;

    @BeforeEach
    void setup() {
        projeto1 = new Projeto();
        projeto1.setId(1L);
        projeto1.setNome("Projeto Teste 1");

        projeto2 = new Projeto();
        projeto2.setId(2L);
        projeto2.setNome("Projeto Teste 2");
    }

    @Test
    void testCriarProjeto() throws Exception {
        Mockito.when(projetoService.salvar(any(Projeto.class))).thenReturn(projeto1);

        mockMvc.perform(post("/api/projetos")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("nome", "Projeto Teste 1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome", is("Projeto Teste 1")));
    }

    @Test
    void testListarProjetos() throws Exception {
        List<Projeto> projetos = Arrays.asList(projeto1, projeto2);

        Mockito.when(projetoService.listar()).thenReturn(projetos);

        mockMvc.perform(get("/api/projetos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is("Projeto Teste 1")))
                .andExpect(jsonPath("$[1].nome", is("Projeto Teste 2")));
    }

    @Test
    void testBuscarProjeto() throws Exception {
        Mockito.when(projetoService.buscarPorId(1L)).thenReturn(projeto1);

        mockMvc.perform(get("/api/projetos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Projeto Teste 1")));
    }

    @Test
    void testBuscarProjetoNotFound() throws Exception {
        Mockito.when(projetoService.buscarPorId(anyLong())).thenReturn(null);

        mockMvc.perform(get("/api/projetos/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAlterarProjeto() throws Exception {
        Mockito.doNothing().when(projetoService).alterar(any(Projeto.class));

        mockMvc.perform(put("/api/projetos")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "1")
                        .param("nome", "Projeto Alterado"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeletarProjeto() throws Exception {
        Mockito.doNothing().when(projetoService).deletar(1L);

        mockMvc.perform(delete("/api/projetos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}