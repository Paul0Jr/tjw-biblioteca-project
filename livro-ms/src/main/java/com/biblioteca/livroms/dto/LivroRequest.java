package com.biblioteca.livroms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LivroRequest(
        @NotBlank(message = "Título obrigatório")
        String titulo,

        @NotBlank(message = "Gênero obrigatório")
        String genero,

        @NotBlank(message = "Ano de publicação obrigatório")
        @Size ( min = 0, max = 4 , message = " Ano de publicação deve possuir apenas 4 dígitos")
        int ano_publicacao,

        @NotBlank(message = "Disponibilidade(?) obrigatória")
        boolean disponivel,

        @NotBlank(message = "Autor obrigatório")
        Long autor_id
) {
}
