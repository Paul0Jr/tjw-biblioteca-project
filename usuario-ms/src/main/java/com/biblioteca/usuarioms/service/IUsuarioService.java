package com.biblioteca.usuarioms.service;

import com.biblioteca.usuarioms.dto.UsuarioRequest;
import com.biblioteca.usuarioms.dto.UsuarioResponse;
import java.util.List;

public interface IUsuarioService {

    UsuarioResponse criar(UsuarioRequest request);
    UsuarioResponse buscarPorId(Long id);
    List<UsuarioResponse> listar();
    void excluir(Long id);
}
