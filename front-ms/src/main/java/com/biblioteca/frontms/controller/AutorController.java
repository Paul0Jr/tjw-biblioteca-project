package com.biblioteca.frontms.controller;

import com.biblioteca.frontms.service.IAutorClient;
import com.biblioteca.frontms.dto.AutorForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.http.HttpStatus;
import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;

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
        return "autores/lista";
    }

    @GetMapping("/novo")
    public String formNovo(Model model) {
        model.addAttribute("autorForm", new AutorForm());
        return "autores/formulario";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("autorForm") AutorForm autorForm,
                         BindingResult result,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "autores/formulario";
        }
        try {
            autorClient.criarAutor(autorForm);
            redirectAttributes.addFlashAttribute("sucesso", "Autor cadastrado com sucesso!");
            return "redirect:/autores";
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
                preencherErros(result, ex);
                return "autores/formulario";
            }
            throw ex;
        }
    }

    @GetMapping("/{id}/editar")
    public String formEditar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            var autor = autorClient.autorById(id);
            var form = new AutorForm();
            form.setId(autor.id());
            form.setNome(autor.nome());
            form.setNacionalidade(autor.nacionalidade());
            form.setAnoNascimento(autor.anoNascimento());
            model.addAttribute("autorForm", form);
            return "autores/formulario";
        } catch (HttpClientErrorException.NotFound ex) {
            redirectAttributes.addFlashAttribute("erro", "Autor não encontrado.");
            return "redirect:/autores";
        }
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute("autorForm") AutorForm autorForm,
                            BindingResult result,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "autores/formulario";
        }
        try {
            autorClient.atualizarAutor(id, autorForm);
            redirectAttributes.addFlashAttribute("sucesso", "Autor atualizado com sucesso!");
            return "redirect:/autores";
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
                preencherErros(result, ex);
                return "autores/formulario";
            }
            throw ex;
        }
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            autorClient.deletarAutor(id);
            redirectAttributes.addFlashAttribute("sucesso", "Autor excluído com sucesso!");
        } catch (HttpClientErrorException.NotFound ex) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir: Autor não encontrado.");
        }
        return "redirect:/autores";
    }

    @ExceptionHandler(RestClientException.class)
    public String tratarErroServico(RestClientException ex, Model model) {
        model.addAttribute("erro", "O serviço de autores está temporariamente indisponível.");
        model.addAttribute("detalhes", ex.getMessage());
        return "erro-servico";
    }

    private void preencherErros(BindingResult result, HttpClientErrorException ex) {
        try {
            var body = ex.getResponseBodyAs(new ParameterizedTypeReference<Map<String, Object>>() {});
            if (body != null) {
                if (body.get("detalhes") instanceof Map<?, ?> detalhes) {
                    detalhes.forEach((campo, mensagem) -> {
                        result.rejectValue((String) campo, "", (String) mensagem);
                    });
                } else if (body.get("erro") instanceof String erroMsg) {
                    result.reject("", erroMsg);
                }
            }
        } catch (Exception e) {
            result.reject("", "Erro de validação no microsserviço.");
        }
    }
}