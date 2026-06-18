package com.biblioteca.frontms.service;

import com.biblioteca.frontms.dto.AutorForm;
import com.biblioteca.frontms.dto.AutorResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class AutorClient implements IAutorClient {

    private final RestClient restClient;

    public AutorClient(RestClient.Builder builder,
                       @Value("${autor.ms.url}") String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl).build();
    }

    public List<AutorResponse> listar() {
        return restClient.get()
                .uri("/api/autores")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public AutorResponse autorById(Long id) {
        return restClient.get()
                .uri("/api/autores/{id}", id)
                .retrieve()
                .body(AutorResponse.class);
    }

    public AutorResponse criarAutor(AutorForm form) {
        return restClient.post()
                .uri("/api/autores")
                .body(form)
                .retrieve()
                .body(AutorResponse.class);
    }

    public AutorResponse atualizarAutor(Long id, AutorForm form) {
        return restClient.put()
                .uri("/api/autores/{id}", id)
                .body(form)
                .retrieve()
                .body(AutorResponse.class);
    }

    public void deletarAutor(Long id) {
        restClient.delete()
                .uri("/api/autores/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }
}