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

    public List<LivroResponse> listar() {
        return livroRepository.findAll().stream().map(this::toResponse).toList();
    }

    public LivroResponse buscarPorId(Long id) {
        return toResponse(obterEntidade(id));
    }

    @Transactional
    public LivroResponse criar(LivroRequest request) {
        if (livroRepository.existBytitulo(request.titulo())) {
            throw new BusinessException("Já existe um livro com o título informado");
        }

        Livro livro = new Livro();
        preencher(livro, request);
        return toResponse(livroRepository.save(livro));
    }

    @Transactional
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
        livro.setAno_publicacao(request.ano_publicacao());
        livro.setDisponivel(request.disponivel());
        livro.setAutor_id(request.autor_id());
    }

    private LivroResponse toResponse(Livro livro) {
        return new LivroResponse(
                livro.getId(),
                livro.getTitulo(),
                livro.getGenero(),
                livro.getAno_publicacao(),
                livro.isDisponivel(),
                livro.getAutor_id()
        );
    }
}
