package com.biblioteca.livroms.service;

import com.biblioteca.livroms.dto.LivroRequest;
import com.biblioteca.livroms.dto.LivroResponse;

public interface ILivroService {
    public LivroResponse criar(LivroRequest request);
    public LivroResponse atualizar(Long id, LivroRequest request);
    public void excluir(Long id);
    public LivroResponse buscarPorId(Long id);
    public java.util.List<LivroResponse> listar();
}
