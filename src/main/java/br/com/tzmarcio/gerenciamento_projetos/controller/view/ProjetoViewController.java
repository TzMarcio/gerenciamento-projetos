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

    @Autowired
    private ProjetoService service;

    @GetMapping
    public String listar(final Model model) {
        model.addAttribute("projetos", service.listar());
        return "projetos/projetos";
    }

    @GetMapping("/novo")
    public String criar(final Model model) {
        model.addAttribute("status", Status.values());
        model.addAttribute("riscos", Risco.values());
        model.addAttribute("projeto", new Projeto());
        return "projetos/projeto-criar";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable final Long id, final Model model) {
        model.addAttribute("projeto", service.buscarPorId(id).orElse(null));
        return "projetos/projeto-criar";
    }

    @GetMapping("/visualizar/{id}")
    public String visualizar(@PathVariable final Long id, final Model model) {
        model.addAttribute("projeto", service.buscarPorId(id).orElse(null));
        model.addAttribute("modo", "visualizar");
        return "projetos/projeto-criar";
    }
}
