package br.com.tzmarcio.gerenciamento_projetos.controller.view;

import br.com.tzmarcio.gerenciamento_projetos.enums.Risco;
import br.com.tzmarcio.gerenciamento_projetos.enums.Status;
import br.com.tzmarcio.gerenciamento_projetos.model.Projeto;
import br.com.tzmarcio.gerenciamento_projetos.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/projetos")
public class ProjetoViewController {

    private static final String PATH_LISTAGEM = "projetos-listagem";
    private static final String PATH_CRIAR = "projeto-criar";

    private static final String VAR_PROJETO = "projeto";

    private final ProjetoService service;

    @Autowired
    ProjetoViewController(final ProjetoService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(final Model model) {
        model.addAttribute("projetos", service.listar());
        return PATH_LISTAGEM;
    }

    @GetMapping("/novo")
    public String criar(final Model model) {
        model.addAttribute("status", Status.values());
        model.addAttribute("riscos", Risco.values());
        model.addAttribute(VAR_PROJETO, new Projeto());
        return PATH_CRIAR;
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable final Long id, final Model model) {
        model.addAttribute(VAR_PROJETO, service.buscarPorId(id).orElse(null));
        return PATH_CRIAR;
    }

    @GetMapping("/visualizar/{id}")
    public String visualizar(@PathVariable final Long id, final Model model) {
        model.addAttribute(VAR_PROJETO, service.buscarPorId(id).orElse(null));
        model.addAttribute("modo", "visualizar");
        return PATH_CRIAR;
    }
}
