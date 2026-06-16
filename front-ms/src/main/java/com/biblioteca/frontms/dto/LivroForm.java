package com.biblioteca.frontms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroForm {

    private Long id;

    @NotBlank(message = "O título é obrigatório.")
    @Size(max = 150, message = "O título deve ter no máximo 150 caracteres.")
    private String titulo;

    @NotBlank(message = "O gênero é obrigatório.")
    @Size(max = 60, message = "O gênero deve ter no máximo 60 caracteres.")
    private String genero;

    @NotNull(message = "O ano de publicação é obrigatório.")
    @Positive(message = "O ano de publicação deve ser positivo.")
    private Integer anoPublicacao;

    @NotNull(message = "Informe se o livro está disponível.")
    private Boolean disponivel = Boolean.TRUE;

    @NotNull(message = "O autor é obrigatório.")
    private Long autorId;
}

