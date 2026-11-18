
package br.edu.escola.BiblioTec.controller.web;

import br.edu.escola.BiblioTec.model.Categoria;
import br.edu.escola.BiblioTec.service.CategoriaService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "categorias/lista";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categorias/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Categoria categoria = categoriaService.buscarPorId(id);
        model.addAttribute("categoria", categoria);
        return "categorias/form";
    }

    @PostMapping
    public String salvar(
            @Valid @ModelAttribute("categoria") Categoria categoria,
            BindingResult resultado,
            Model model) {

        if (resultado.hasErrors()) {
            return "categorias/form";
        }

        try {
            categoriaService.salvar(categoria);
            return "redirect:/categorias";

        } catch (IllegalArgumentException e) {
            resultado.rejectValue("nome", "categoria.duplicada", e.getMessage());
            return "categorias/form";
        }
    }

    @PostMapping("/{id}/excluir")
    public String excluir(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        try {
            categoriaService.excluir(id);

            redirectAttributes.addFlashAttribute(
                    "mensagem", "Categoria excluída com sucesso!"
            );

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute(
                    "erro", e.getMessage()
            );
        }

        return "redirect:/categorias";
    }
}
