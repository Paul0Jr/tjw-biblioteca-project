package com.biblioteca.frontms.service;

import com.biblioteca.frontms.dto.AutorForm;
import com.biblioteca.frontms.dto.AutorResponse;

import java.util.List;

public interface IAutorClient {
        List<AutorResponse> listar();
        AutorResponse autorById(Long id);
        AutorResponse criarAutor(AutorForm autorForm);
        AutorResponse atualizarAutor(Long id, AutorForm autorForm);
        void deletarAutor(Long id);
}
