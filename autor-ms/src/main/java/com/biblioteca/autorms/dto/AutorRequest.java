package com.biblioteca.autorms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record AutorRequest(
        @NotBlank(message = "Nome obrigatório")
        String nome,

        @NotBlank(message = "Nacionalidade obrigatória")
        String nacionalidade,

        @Positive(message = "Ano de nascimento deve ser positivo")
        Integer anoNascimento
) {
}
