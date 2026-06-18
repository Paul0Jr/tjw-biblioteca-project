package com.biblioteca.frontms.dto;

public record LivroExibicao(
        Long id,
        String titulo,
        String genero,
        Integer anoPublicacao,
        Boolean disponivel,
        Long autorId,
        String nomeAutor
) {
}
