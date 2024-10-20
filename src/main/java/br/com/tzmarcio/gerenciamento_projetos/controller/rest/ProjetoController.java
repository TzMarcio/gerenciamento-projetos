package br.com.tzmarcio.gerenciamento_projetos.controller.rest;

import br.com.tzmarcio.gerenciamento_projetos.model.Projeto;
import br.com.tzmarcio.gerenciamento_projetos.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projetos")
public class ProjetoController {

    @Autowired
    private ProjetoService service;

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Projeto> criar(final Projeto projeto){
        return ResponseEntity.ok(this.service.salvar(projeto));
    }

    @GetMapping
    public ResponseEntity<List<Projeto>> listar(){
        return ResponseEntity.ok(this.service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Projeto> buscar(@PathVariable final Long id){
        Optional<Projeto> projeto = this.service.buscarPorId(id);
        return projeto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<Projeto> alterar(@RequestBody final Projeto projeto){
        this.service.alterar(projeto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable final Long id) throws Exception {
        this.service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
