package com.biblioteca.frontms.controller;

import com.biblioteca.frontms.dto.LivroForm;
import com.biblioteca.frontms.dto.LivroExibicao;
import com.biblioteca.frontms.service.ILivroClient;
import com.biblioteca.frontms.service.IAutorClient;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.http.HttpStatus;
import org.springframework.core.ParameterizedTypeReference;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/livros")
public class LivroController {

    private final ILivroClient livroClient;
    private final IAutorClient autorClient;

    public LivroController(ILivroClient livroClient, IAutorClient autorClient) {
        this.livroClient = livroClient;
        this.autorClient = autorClient;
    }

    @GetMapping
    public String listar(
            @RequestParam(required = false) Boolean disponivel,
            @RequestParam(required = false) Long autorId,
            Model model) {
        
        List<com.biblioteca.frontms.dto.LivroResponse> livrosResponse = livroClient.listar(disponivel, autorId);
        
        // Fetch author names in bulk (or specific author) to avoid N+1 query problem
        java.util.Map<Long, String> autorIdToNomeMap = new java.util.HashMap<>();
        
        if (autorId != null) {
            try {
                var autor = autorClient.autorById(autorId);
                if (autor != null) {
                    autorIdToNomeMap.put(autorId, autor.nome());
                    model.addAttribute("autorFiltrado", autor);
                }
            } catch (Exception e) {
                autorIdToNomeMap.put(autorId, "Autor removido");
            }
        } else {
            try {
                autorClient.listar().forEach(a -> autorIdToNomeMap.put(a.id(), a.nome()));
            } catch (Exception e) {
                // Keep empty if service is down
            }
        }

        List<LivroExibicao> livros = livrosResponse.stream()
                .map(l -> new LivroExibicao(
                        l.id(),
                        l.titulo(),
                        l.genero(),
                        l.anoPublicacao(),
                        l.disponivel(),
                        l.autorId(),
                        autorIdToNomeMap.getOrDefault(l.autorId(), "Autor removido")
                ))
                .toList();

        model.addAttribute("livros", livros);
        model.addAttribute("disponivelSelecionado", disponivel);
        model.addAttribute("autorIdSelecionado", autorId);
        return "livros/lista";
    }

    @GetMapping("/novo")
    public String formNovo(Model model) {
        model.addAttribute("livroForm", new LivroForm());
        model.addAttribute("autores", autorClient.listar());
        return "livros/formulario";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("livroForm") LivroForm livroForm,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("autores", autorClient.listar());
            return "livros/formulario";
        }
        try {
            livroClient.criarLivro(livroForm);
            redirectAttributes.addFlashAttribute("sucesso", "Livro cadastrado com sucesso!");
            return "redirect:/livros";
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
                preencherErros(result, ex);
                model.addAttribute("autores", autorClient.listar());
                return "livros/formulario";
            }
            throw ex;
        }
    }

    @GetMapping("/{id}/editar")
    public String formEditar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            var livro = livroClient.livroById(id);
            var form = new LivroForm();
            form.setId(livro.id());
            form.setTitulo(livro.titulo());
            form.setGenero(livro.genero());
            form.setDisponivel(livro.disponivel());
            form.setAnoPublicacao(livro.anoPublicacao());
            form.setAutorId(livro.autorId());

            model.addAttribute("livroForm", form);
            model.addAttribute("autores", autorClient.listar());
            return "livros/formulario";
        } catch (HttpClientErrorException.NotFound ex) {
            redirectAttributes.addFlashAttribute("erro", "Livro não encontrado.");
            return "redirect:/livros";
        }
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute("livroForm") LivroForm livroForm,
                            BindingResult result,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("autores", autorClient.listar());
            return "livros/formulario";
        }
        try {
            livroClient.atualizarLivro(id, livroForm);
            redirectAttributes.addFlashAttribute("sucesso", "Livro atualizado com sucesso!");
            return "redirect:/livros";
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
                preencherErros(result, ex);
                model.addAttribute("autores", autorClient.listar());
                return "livros/formulario";
            }
            throw ex;
        }
    }

    @GetMapping("/{id}")
    public String detalhe(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            var l = livroClient.livroById(id);
            var exibicao = new LivroExibicao(
                    l.id(),
                    l.titulo(),
                    l.genero(),
                    l.anoPublicacao(),
                    l.disponivel(),
                    l.autorId(),
                    obterNomeAutor(l.autorId())
            );
            model.addAttribute("livro", exibicao);
            return "livros/detalhe";
        } catch (HttpClientErrorException.NotFound ex) {
            redirectAttributes.addFlashAttribute("erro", "Livro não encontrado.");
            return "redirect:/livros";
        }
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            livroClient.deletarLivro(id);
            redirectAttributes.addFlashAttribute("sucesso", "Livro excluído com sucesso!");
        } catch (HttpClientErrorException.NotFound ex) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir: Livro não encontrado.");
        }
        return "redirect:/livros";
    }

    @ExceptionHandler(RestClientException.class)
    public String tratarErroServico(RestClientException ex, Model model) {
        model.addAttribute("erro", "O serviço de livros ou autores está temporariamente indisponível.");
        model.addAttribute("detalhes", ex.getMessage());
        return "erro-servico";
    }

    private String obterNomeAutor(Long autorId) {
        try {
            var autor = autorClient.autorById(autorId);
            return autor != null ? autor.nome() : "Autor removido";
        } catch (Exception e) {
            return "Autor removido";
        }
    }

    private void preencherErros(BindingResult result, HttpClientErrorException ex) {
        try {
            var body = ex.getResponseBodyAs(new ParameterizedTypeReference<Map<String, Object>>() {});
            if (body != null) {
                if (body.get("detalhes") instanceof Map<?, ?> detalhes) {
                    detalhes.forEach((campo, message) -> {
                        result.rejectValue((String) campo, "", (String) message);
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