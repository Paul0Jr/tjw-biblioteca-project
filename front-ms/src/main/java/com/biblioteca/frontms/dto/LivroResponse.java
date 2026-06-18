package com.biblioteca.frontms.dto;

public record LivroResponse(
        Long id,
        String titulo,
        String genero,
        Integer anoPublicacao,
        Boolean disponivel,
        Long autorId
) {
}
