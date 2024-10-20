package br.com.tzmarcio.gerenciamento_projetos.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeViewController {

    private static final String PATH_LISTAGEM = "projetos-listagem";

    @GetMapping("/")
    public String home() {
        return PATH_LISTAGEM;
    }
}
