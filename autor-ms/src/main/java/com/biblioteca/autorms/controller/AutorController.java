package com.biblioteca.autorms.controller;

import com.biblioteca.autorms.dto.AutorRequest;
import com.biblioteca.autorms.dto.AutorResponse;
import com.biblioteca.autorms.service.IAutorService;
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
@RequestMapping("/api/autores")
public class AutorController {

    private final IAutorService autorService;

    public AutorController(IAutorService autorService) {
        this.autorService = autorService;
    }


    @GetMapping
    public List<AutorResponse> listar() {
        return autorService.listar();
    }


    @GetMapping("/{id}")
    public AutorResponse buscarPorId(@PathVariable Long id) {
        return autorService.buscarPorId(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AutorResponse criar(@Valid @RequestBody AutorRequest request) {
        return autorService.criar(request);
    }


    @PutMapping("/{id}")
    public AutorResponse atualizar(@PathVariable Long id, @Valid @RequestBody AutorRequest request) {
        return autorService.atualizar(id, request);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        autorService.excluir(id);
    }
}