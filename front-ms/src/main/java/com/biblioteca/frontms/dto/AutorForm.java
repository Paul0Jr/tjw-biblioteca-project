package com.biblioteca.frontms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AutorForm {

    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @NotBlank(message = "A nacionalidade é obrigatória.")
    private String nacionalidade;

    @NotNull(message = "O ano de nascimento é obrigatório.")
    @Positive(message = "O ano de nascimento deve ser positivo.")
    private Integer anoNascimento;

}

