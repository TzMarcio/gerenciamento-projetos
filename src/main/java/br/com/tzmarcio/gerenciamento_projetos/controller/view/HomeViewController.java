package br.com.tzmarcio.gerenciamento_projetos.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeViewController {

    private final ProjetoViewController projetoViewController;

    @Autowired
    HomeViewController(final ProjetoViewController controller){
        this.projetoViewController = controller;
    }

    @GetMapping("/")
    public String home(final Model model) {
        return projetoViewController.listar(model);
    }
}
