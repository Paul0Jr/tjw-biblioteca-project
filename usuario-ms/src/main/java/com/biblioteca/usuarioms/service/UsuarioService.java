package com.biblioteca.usuarioms.service;

import com.biblioteca.usuarioms.exception.ResourceNotFoundException;
import com.biblioteca.usuarioms.repository.UsuarioRepository;
import com.biblioteca.usuarioms.dto.*;
import com.biblioteca.usuarioms.entity.Usuario;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    @Override
    public UsuarioResponse criar(UsuarioRequest request) {
        // 1. Instancia a entidade e popula com os dados que vieram na requisição
        Usuario usuario = new Usuario();
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setSenha(request.senha()); // Dica: No futuro, aplique hash de senha aqui (ex: BCrypt)

        // 2. Salva a entidade no banco de dados usando seu Repository
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        // 3. Retorna o record UsuarioResponse passando os dados gerados/salvos
        return new UsuarioResponse(
            usuarioSalvo.getId(),
            usuarioSalvo.getNome(),
            usuarioSalvo.getEmail()
        );
    }


    @Override
    public UsuarioResponse buscarPorId(Long id) {
        return toResponse(obterEntidade(id));}



    @Override
    public List<UsuarioResponse> listar() {
        return usuarioRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional
    @Override
    public void excluir(Long id) {
        Usuario usuario = obterEntidade(id);
        usuarioRepository.delete(usuario);
    }

    private Usuario obterEntidade(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + id));
    }

    private UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }
}
