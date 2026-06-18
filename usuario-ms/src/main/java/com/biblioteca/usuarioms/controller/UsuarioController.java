package com.biblioteca.usuarioms.controller;

import com.biblioteca.usuarioms.dto.UsuarioRequest;
import com.biblioteca.usuarioms.dto.UsuarioResponse;
import com.biblioteca.usuarioms.service.IUsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários e funcionários")
public class UsuarioController {

    private final IUsuarioService usuarioService;

    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @Operation(summary = "Testar conectividade", description = "Retorna uma string simples indicando se o microsserviço está online")
    public String test() {
        return "Usuario MS is running!";
    }

    // NOVO ENDPOINT DE CADASTRO
    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED) // Retorna o código HTTP 201 
    @Operation(summary = "Cadastrar um novo usuário", description = "Cria um registro de leitor ou funcionário e retorna seus dados públicos")
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso")
    public UsuarioResponse criar(@RequestBody UsuarioRequest request) {
        return usuarioService.criar(request);
    }
}