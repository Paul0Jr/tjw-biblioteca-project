package com.biblioteca.livroms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record LivroRequest(
        @NotBlank(message = "Título obrigatório")
        @Size(max = 150, message = "Título não pode exceder 150 caracteres")
        String titulo,

        @NotBlank(message = "Gênero obrigatório")
        @Size(max = 60, message = "Gênero não pode exceder 60 caracteres")
        String genero,

        @NotNull(message = "Ano de publicação obrigatório")
        @Positive(message = "Ano de publicação deve ser um número positivo")
        Integer anoPublicacao,

        @NotNull(message = "Disponibilidade obrigatória")
        Boolean disponivel,

        @NotNull(message = "ID do autor obrigatório")
        @Positive(message = "ID do autor deve ser um número positivo")
        Long autorId
) {
}
