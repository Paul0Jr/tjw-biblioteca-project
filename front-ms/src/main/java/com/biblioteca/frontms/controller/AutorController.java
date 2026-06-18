// controller/AutorController.java
package com.biblioteca.frontms.controller;

import com.biblioteca.frontms.service.IAutorClient;
import com.biblioteca.frontms.dto.AutorForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/autores")
public class AutorController {

    private final IAutorClient autorClient;

    public AutorController(IAutorClient autorClient) {
        this.autorClient = autorClient;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("autores", autorClient.listar());
        return "autor/lista";
    }

    @GetMapping("/novo")
    public String formNovo(Model model) {
        model.addAttribute("autorForm", new AutorForm());
        return "autor/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute AutorForm autorForm,
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "autor/form";
        }
        if (autorForm.getId() == null) {
            autorClient.criarAutor(autorForm);
        } else {
            autorClient.atualizarAutor(autorForm.getId(), autorForm);
        }
        return "redirect:/autores";
    }

    @GetMapping("/editar/{id}")
    public String formEditar(@PathVariable Long id, Model model) {
        var autor = autorClient.autorById(id);
        var form = new AutorForm();
        form.setId(autor.id());
        form.setNome(autor.nome());
        form.setNacionalidade(autor.nacionalidade());
        form.setAnoNascimento(autor.anoNascimento());
        model.addAttribute("autorForm", form);
        return "autor/form";
    }

    @GetMapping("/deletar/{id}")
    public String deletarAutor(@PathVariable Long id) {
        autorClient.deletarAutor(id);
        return "redirect:/autores";
    }
}