package com.biblioteca.frontms.service;

import com.biblioteca.frontms.dto.LivroForm;
import com.biblioteca.frontms.dto.LivroResponse;

import java.util.List;

public interface ILivroClient {
    List<LivroResponse> listar(Boolean disponivel, Long autorId);
    LivroResponse livroById(Long id);
    LivroResponse criarLivro(LivroForm livroForm);
    LivroResponse atualizarLivro(Long id, LivroForm livroForm);
    void deletarLivro(Long id);
}
