package com.biblioteca.autorms.controller;

import com.biblioteca.autorms.dto.AutorRequest;
import com.biblioteca.autorms.dto.AutorResponse;
import com.biblioteca.autorms.service.IAutorService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/autores")
@Tag(name = "Autores", description = "Endpoints para gerenciamento de autores")
public class AutorController {

    private final IAutorService autorService;

    public AutorController(IAutorService autorService) {
        this.autorService = autorService;
    }


    @GetMapping
    @Operation(summary = "Listar todos os autores", description = "Retorna uma lista contendo todos os autores cadastrados")
    public List<AutorResponse> listar() {
        return autorService.listar();
    }


    @GetMapping("/{id}")
    @Operation(summary = "Buscar autor por ID", description = "Retorna os detalhes de um autor específico com base no ID fornecido")
    public AutorResponse buscarPorId(@PathVariable Long id) {
        return autorService.buscarPorId(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar um novo autor", description = "Cria um novo autor no sistema")
    public AutorResponse criar(@Valid @RequestBody AutorRequest request) {
        return autorService.criar(request);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um autor existente", description = "Substitui completamente os dados de um autor existente")
    public AutorResponse atualizar(@PathVariable Long id, @Valid @RequestBody AutorRequest request) {
        return autorService.atualizar(id, request);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir um autor", description = "Remove um autor do sistema")
    public void excluir(@PathVariable Long id) {
        autorService.excluir(id);
    }
}