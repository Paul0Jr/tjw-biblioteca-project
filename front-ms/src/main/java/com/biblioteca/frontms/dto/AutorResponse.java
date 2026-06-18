package com.biblioteca.frontms.dto;

public record AutorResponse (
        Long id,
        String nome,
        String nacionalidade,
        Integer anoNascimento
){
}
