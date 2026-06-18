// controller/AutorController.java
package com.biblioteca.frontms.controller;

import com.biblioteca.frontms.service.ILivroClient;
import com.biblioteca.frontms.dto.LivroForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/livros")
public class LivroController {

    private final ILivroClient livroClient;

    public LivroController(ILivroClient livroClient) {
        this.livroClient = livroClient;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("livros", livroClient.listar());
        return "livro/lista";
    }

    @GetMapping("/novo")
    public String formNovo(Model model) {
        model.addAttribute("livroForm", new LivroForm());
        return "livro/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute LivroForm livroForm,
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "livro/form";
        }
        if (livroForm.getId() == null) {
            livroClient.criarLivro(livroForm);
        } else {
            livroClient.atualizarLivro(livroForm.getId(), livroForm);
        }
        return "redirect:/livros";
    }

    @GetMapping("/editar/{id}")
    public String formEditar(@PathVariable Long id, Model model) {
        var livro = livroClient.livroById(id);
        var form = new LivroForm();
        form.setId(livro.id());
        form.setTitulo(livro.titulo());
        form.setGenero(livro.genero());
        form.setDisponivel(livro.disponivel());
        form.setAnoPublicacao(livro.anoPublicacao());
        form.setAutorId(livro.autorId());
        model.addAttribute("autorForm", form);
        return "autor/form";
    }

    @GetMapping("/deletar/{id}")
    public String excluir(@PathVariable Long id) {
        livroClient.deletarLivro(id);
        return "redirect:/livros";
    }
}