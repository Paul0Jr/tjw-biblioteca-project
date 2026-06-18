package com.biblioteca.usuarioms.dto;

public record UsuarioRequest(
        String nome,
        String email,
        String senha
) {
}
