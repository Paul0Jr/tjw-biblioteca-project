package com.biblioteca.autorms.service;
import com.biblioteca.autorms.dto.AutorRequest;
import com.biblioteca.autorms.dto.AutorResponse;

public interface IAutorService {
    public AutorResponse criar(AutorRequest request);
    public AutorResponse atualizar(Long id, AutorRequest request);
    public void excluir(Long id);
    public AutorResponse buscarPorId(Long id);
    public java.util.List<AutorResponse> listar();
}
