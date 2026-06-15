package com.biblioteca.livroms.dto;

public record LivroResponse(
        Long id,
        String titulo,
        String genero,
        int ano_publicacao,
        boolean disponivel,
        Long autor_id
) {
}
