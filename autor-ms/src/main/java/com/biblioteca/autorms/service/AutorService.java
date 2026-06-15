package com.biblioteca.autorms.service;

import com.biblioteca.autorms.repository.AutorRepository;
import com.biblioteca.autorms.dto.*;
import com.biblioteca.autorms.entity.Autor;
import com.biblioteca.autorms.exception.BusinessException;
import com.biblioteca.autorms.exception.ResourceNotFoundException;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


public class AutorService implements IAutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<AutorResponse> listar() {
        return autorRepository.findAll().stream().map(this::toResponse).toList();
    }

    public AutorResponse buscarPorId(Long id) {
        return toResponse(obterEntidade(id));
    }

    @Transactional
    public AutorResponse criar(AutorRequest request) {
        if (autorRepository.existByNome(request.nome())) {
            throw new BusinessException("Já existe um autor com o nome informado");
        }

        Autor autor = new Autor();
        preencher(autor, request);
        return toResponse(autorRepository.save(autor));
    }

    @Transactional
    public AutorResponse atualizar(Long id, AutorRequest request) {
        Autor autor = obterEntidade(id);

        autorRepository.findByNome(request.nome())
                .filter(outro -> !outro.getId().equals(id))
                .ifPresent(outro -> {
                    throw new BusinessException("Já existe um autor com o nome informado");
                });

        preencher(autor, request);
        return toResponse(autorRepository.save(autor));
    }

    @Transactional
    public void excluir(Long id) {
        Autor autor = obterEntidade(id);
        autorRepository.delete(autor);
    }

    private Autor obterEntidade(Long id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado: " + id));
    }

    private void preencher(Autor autor, AutorRequest request) {
        autor.setNome(request.nome());
        autor.setNacionalidade(request.nacionalidade());
        autor.setAno_nascimento(request.ano_nascimento());
    }

    private AutorResponse toResponse(Autor autor) {
        return new AutorResponse(
                autor.getId(),
                autor.getNome(),
                autor.getNacionalidade(),
                autor.getAno_nascimento()
        );
    }
}
