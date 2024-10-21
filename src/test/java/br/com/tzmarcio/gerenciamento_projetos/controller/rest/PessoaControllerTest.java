package br.com.tzmarcio.gerenciamento_projetos.controller.rest;

import br.com.tzmarcio.gerenciamento_projetos.model.Pessoa;
import br.com.tzmarcio.gerenciamento_projetos.service.PessoaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@WebMvcTest(PessoaController.class)
class PessoaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PessoaService pessoaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Pessoa pessoaGerente;
    private Pessoa pessoaFuncionario;

    @BeforeEach
    void setup() {
        pessoaGerente = new Pessoa();
        pessoaGerente.setNome("Gerente Teste");

        pessoaFuncionario = new Pessoa();
        pessoaFuncionario.setNome("Funcionario Teste");
    }

    @Test
    void testAutocompleteGerentes() throws Exception {
        List<Pessoa> gerentes = Collections.singletonList(pessoaGerente);

        Mockito.when(pessoaService.buscarGerentes(anyString())).thenReturn(gerentes);

        mockMvc.perform(get("/api/pessoa/autocomplete/gerentes")
                        .param("nome", "Gerente")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("Gerente Teste")));
    }

    @Test
    void testAutocompleteFuncionarios() throws Exception {
        List<Pessoa> funcionarios = Collections.singletonList(pessoaFuncionario);

        Mockito.when(pessoaService.buscarFuncionarios(anyString())).thenReturn(funcionarios);

        mockMvc.perform(get("/api/pessoa/autocomplete/funcionarios")
                        .param("nome", "Funcionario")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("Funcionario Teste")));
    }

    @Test
    void testCriarPessoa() throws Exception {
        Pessoa novaPessoa = new Pessoa();
        novaPessoa.setNome("Nova Pessoa");

        Mockito.when(pessoaService.salvar(any(Pessoa.class))).thenReturn(novaPessoa);

        mockMvc.perform(post("/api/pessoa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novaPessoa)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome", is("Nova Pessoa")));
    }
}