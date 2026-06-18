package com.biblioteca.frontms.service;

import com.biblioteca.frontms.dto.LivroForm;
import com.biblioteca.frontms.dto.LivroResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class LivroClient implements ILivroClient {
    private final RestClient restClient;

    public LivroClient(RestClient.Builder builder,
                       @Value("${livro.ms.url}") String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl).build();
    }

    public List<LivroResponse> listar() {
        return restClient.get()
                .uri("/api/livros")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public LivroResponse livroById(Long id) {
        return restClient.get()
                .uri("/api/livros/{id}", id)
                .retrieve()
                .body(LivroResponse.class);
    }

    public LivroResponse criarLivro(LivroForm form) {
        return restClient.post()
                .uri("/api/livros")
                .body(form)
                .retrieve()
                .body(LivroResponse.class);
    }

    public LivroResponse atualizarLivro(Long id, LivroForm form) {
        return restClient.put()
                .uri("/api/livro/{id}", id)
                .body(form)
                .retrieve()
                .body(LivroResponse.class);
    }

    public void deletarLivro(Long id) {
        restClient.delete()
                .uri("/api/livros/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }
}
