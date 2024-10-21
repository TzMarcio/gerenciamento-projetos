package br.com.tzmarcio.gerenciamento_projetos.controller.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeViewController.class)
class HomeViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjetoViewController projetoViewController;

    @BeforeEach
    void setup() {
        Mockito.when(projetoViewController.listar(any(Model.class))).thenReturn("projetos/listagem");
    }

    @Test
    void testHome() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("projetos/listagem"));
    }
}