package com.biblioteca.autorms.service;
import com.biblioteca.autorms.dto.AutorRequest;
import com.biblioteca.autorms.dto.AutorResponse;

public interface IAutorService {
    AutorResponse criar(AutorRequest request);
    AutorResponse atualizar(Long id, AutorRequest request);
    void excluir(Long id);
    AutorResponse buscarPorId(Long id);
    java.util.List<AutorResponse> listar();
}
