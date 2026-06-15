package com.biblioteca.autorms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AutorRequest(
        @NotBlank(message = "Nome obrigatório")
        String nome,

        @NotBlank(message = "Nacionalidade obrigatória")
        String nacionalidade,

        @NotBlank(message = "Ano de publicação obrigatório")
        @Size( min = 0, max = 4 , message = " Ano de nascimento deve possuir apenas 4 dígitos")
        int ano_nascimento
) {
}
