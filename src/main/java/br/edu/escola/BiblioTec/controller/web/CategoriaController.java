package br.edu.escola.BiblioTec.controller.web;

import br.edu.escola.BiblioTec.service.CategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(Model model) {
        // Disponibiliza a coleção para o Thymeleaf.
        model.addAttribute("categorias", categoriaService.listarTodas());

        // Nome do template, sem a extensão .html.
        return "categorias/lista";
    }
}
