package com.biblioteca.livroms.service;

import com.biblioteca.livroms.repository.LivroRepository;
import com.biblioteca.livroms.dto.*;
import com.biblioteca.livroms.entity.Livro;
import com.biblioteca.livroms.exception.BusinessException;
import com.biblioteca.livroms.exception.ResourceNotFoundException;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LivroService implements ILivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    @Override
    public List<LivroResponse> listar() {
        return livroRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public List<LivroResponse> listarPorDisponibilidade(Boolean disponivel) {
        if (disponivel == null) {
            return listar();
        }
        return livroRepository.findByDisponivel(disponivel)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public LivroResponse buscarPorId(Long id) {
        return toResponse(obterEntidade(id));
    }

    @Transactional
    @Override
    public LivroResponse criar(LivroRequest request) {
        livroRepository.findByTitulo(request.titulo())
                .ifPresent(outro -> {
                    throw new BusinessException("Já existe um livro com o título informado");
                });

        Livro livro = new Livro();
        preencher(livro, request);
        return toResponse(livroRepository.save(livro));
    }

    @Transactional
    @Override
    public LivroResponse atualizar(Long id, LivroRequest request) {
        Livro livro = obterEntidade(id);

        livroRepository.findByTitulo(request.titulo())
                .filter(outro -> !outro.getId().equals(id))
                .ifPresent(outro -> {
                    throw new BusinessException("Já existe um livro com o título informado");
                });

        preencher(livro, request);
        return toResponse(livroRepository.save(livro));
    }

    @Transactional
    @Override
    public void excluir(Long id) {
        Livro livro = obterEntidade(id);
        livroRepository.delete(livro);
    }

    private Livro obterEntidade(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado: " + id));
    }

    private void preencher(Livro livro, LivroRequest request) {
        livro.setTitulo(request.titulo());
        livro.setGenero(request.genero());
        livro.setAnoPublicacao(request.anoPublicacao());
        livro.setDisponivel(request.disponivel());
        livro.setAutorId(request.autorId());
    }

    private LivroResponse toResponse(Livro livro) {
        return new LivroResponse(
                livro.getId(),
                livro.getTitulo(),
                livro.getGenero(),
                livro.getAnoPublicacao(),
                livro.getDisponivel(),
                livro.getAutorId()
        );
    }
}
