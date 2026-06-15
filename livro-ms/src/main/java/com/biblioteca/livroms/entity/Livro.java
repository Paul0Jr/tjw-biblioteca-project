package com.biblioteca.livroms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "livro")
@Getter
@Setter
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String genero;

    @Column(nullable = false)
    private int ano_publicacao;

    @Column(nullable = false)
    private boolean disponivel;

    @Column(nullable = false)
    private Long autor_id;

    public Livro() {
    }

    public Livro(String titulo, String genero, int ano_publicacao, boolean disponivel, Long autor_id) {
        this.titulo = titulo;
        this.genero = genero;
        this.ano_publicacao = ano_publicacao;
        this.disponivel = disponivel;
        this.autor_id = autor_id;
    }
}