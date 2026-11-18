
package br.edu.escola.BiblioTec.controller.web;

import br.edu.escola.BiblioTec.model.Livro;
import br.edu.escola.BiblioTec.service.LivroService;
import br.edu.escola.BiblioTec.service.CategoriaService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;
    private final CategoriaService categoriaService;

    public LivroController(
            LivroService livroService,
            CategoriaService categoriaService) {
        this.livroService = livroService;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(
            @RequestParam(required = false) String titulo,
            Model model) {

        model.addAttribute(
                "livros",
                livroService.pesquisarPorTitulo(titulo)
        );

        model.addAttribute("titulo", titulo);

        return "livros/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("livro", new Livro());
        carregarCategorias(model);
        return "livros/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("livro", livroService.buscarPorId(id));
        carregarCategorias(model);
        return "livros/form";
    }

    @PostMapping
    public String salvar(
            @Valid @ModelAttribute("livro") Livro livro,
            BindingResult resultado,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (resultado.hasErrors()) {
            carregarCategorias(model);
            return "livros/form";
        }

        try {
            livroService.salvar(livro);

            redirectAttributes.addFlashAttribute(
                    "mensagem", "Livro salvo com sucesso!"
            );

            return "redirect:/livros";

        } catch (IllegalArgumentException e) {
            resultado.reject("erro", e.getMessage());
            carregarCategorias(model);
            return "livros/form";
        }
    }

    @PostMapping("/{id}/excluir")
    public String excluir(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        livroService.excluir(id);

        redirectAttributes.addFlashAttribute(
                "mensagem", "Livro excluído com sucesso!"
        );

        return "redirect:/livros";
    }

    private void carregarCategorias(Model model) {
        model.addAttribute(
                "categorias",
                categoriaService.listarTodas()
        );
    }
}
