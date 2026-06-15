package com.biblioteca.livroms.service;

import com.biblioteca.livroms.dto.LivroRequest;
import com.biblioteca.livroms.dto.LivroResponse;
import java.util.List;

public interface ILivroService {
    LivroResponse criar(LivroRequest request);
    LivroResponse atualizar(Long id, LivroRequest request);
    void excluir(Long id);
    LivroResponse buscarPorId(Long id);
    List<LivroResponse> listar();
    List<LivroResponse> listarPorDisponibilidade(Boolean disponivel);
}
