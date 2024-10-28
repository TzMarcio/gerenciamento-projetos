package br.com.tzmarcio.gerenciamento_projetos.controller.rest;

import br.com.tzmarcio.gerenciamento_projetos.model.Pessoa;
import br.com.tzmarcio.gerenciamento_projetos.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pessoa")
public class PessoaController {

    private final PessoaService service;

    @Autowired
    PessoaController(final PessoaService service) {
        this.service = service;
    }

    @GetMapping("/autocomplete/gerentes")
    public ResponseEntity<List<Pessoa>> autocompleteGerentes(@RequestParam("nome") final String nome) {
        return ResponseEntity.ok(this.service.buscarGerentes(nome));
    }

    @GetMapping("/autocomplete/funcionarios")
    public ResponseEntity<List<Pessoa>> autocompleteFuncionarios(@RequestParam("nome") final String nome) {
        return ResponseEntity.ok(this.service.buscarFuncionarios(nome));
    }

    @PostMapping
    public ResponseEntity<Pessoa> criar(@RequestBody final Pessoa pessoa) {
        return new ResponseEntity<>(this.service.salvar(pessoa), HttpStatus.CREATED);
    }

}
