package com.biblioteca.autorms.dto;

public record AutorResponse(
        Long id,
        String nome,
        String nacionalidade,
        Integer anoNascimento
) {
}
