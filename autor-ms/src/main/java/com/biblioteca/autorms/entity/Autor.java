package com.biblioteca.autorms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "autor")
@Getter
@Setter
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String nacionalidade;

    @Column(nullable = false)
    private int ano_nascimento;

    public Autor() {
    }

    public Autor(String nome, String nacionalidade, int ano_nascimento) {
        this.nome = nome;
        this.nacionalidade = nacionalidade;
        this.ano_nascimento = ano_nascimento;
    }
}