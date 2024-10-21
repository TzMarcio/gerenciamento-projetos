package br.com.tzmarcio.gerenciamento_projetos.controller.view;


import br.com.tzmarcio.gerenciamento_projetos.model.Projeto;
import br.com.tzmarcio.gerenciamento_projetos.service.ProjetoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjetoViewController.class)
class ProjetoViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjetoService projetoService;

    @BeforeEach
    void setup() {
        Mockito.when(projetoService.listar()).thenReturn(Collections.emptyList());
        Mockito.when(projetoService.buscarPorId(anyLong())).thenReturn(new Projeto());
    }

    @Test
    void testListar() throws Exception {
        mockMvc.perform(get("/projetos"))
                .andExpect(status().isOk())
                .andExpect(view().name("projetos-listagem"))
                .andExpect(model().attributeExists("projetos"));
    }

    @Test
    void testCriar() throws Exception {
        mockMvc.perform(get("/projetos/novo"))
                .andExpect(status().isOk())
                .andExpect(view().name("projeto-criar"))
                .andExpect(model().attributeExists("status"))
                .andExpect(model().attributeExists("riscos"))
                .andExpect(model().attributeExists("projeto"));
    }

    @Test
    void testEditar() throws Exception {
        mockMvc.perform(get("/projetos/editar/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("projeto-criar"))
                .andExpect(model().attributeExists("projeto"))
                .andExpect(model().attributeExists("status"))
                .andExpect(model().attributeExists("riscos"))
                .andExpect(model().attributeExists("pessoas"));
    }

    @Test
    void testVisualizar() throws Exception {
        mockMvc.perform(get("/projetos/visualizar/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("projeto-criar"))
                .andExpect(model().attributeExists("projeto"))
                .andExpect(model().attributeExists("status"))
                .andExpect(model().attributeExists("riscos"))
                .andExpect(model().attributeExists("pessoas"))
                .andExpect(model().attribute("modo", "visualizar"));
    }
}