package com.biblioteca.livroms.controller;

import com.biblioteca.livroms.dto.LivroRequest;
import com.biblioteca.livroms.dto.LivroResponse;
import com.biblioteca.livroms.service.ILivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/livros")
@Tag(name = "Livros", description = "Endpoints para gerenciamento de livros")
public class LivroController {

    private final ILivroService livroService;

    public LivroController(ILivroService livroService) {
        this.livroService = livroService;
    }


    @GetMapping
    @Operation(summary = "Listar livros", description = "Retorna uma lista de todos os livros cadastrados, com opção de filtrar por disponibilidade ou autor")
    public List<LivroResponse> listar(
            @RequestParam(required = false) Boolean disponivel,
            @RequestParam(required = false) Long autorId) {
        return livroService.listarPorFiltros(disponivel, autorId);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Buscar livro por ID", description = "Retorna os detalhes de um livro específico com base no ID fornecido")
    public LivroResponse buscarPorId(@PathVariable Long id) {
        return livroService.buscarPorId(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar um novo livro", description = "Cria um novo livro no sistema")
    public LivroResponse criar(@Valid @RequestBody LivroRequest request) {
        return livroService.criar(request);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um livro existente", description = "Substitui completamente os dados de um livro existente")
    public LivroResponse atualizar(@PathVariable Long id, @Valid @RequestBody LivroRequest request) {
        return livroService.atualizar(id, request);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir um livro", description = "Remove um livro do acervo")
    public void excluir(@PathVariable Long id) {
        livroService.excluir(id);
    }
}