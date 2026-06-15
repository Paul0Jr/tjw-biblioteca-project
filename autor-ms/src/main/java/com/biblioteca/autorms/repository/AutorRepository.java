package com.biblioteca.autorms.repository;

import com.biblioteca.autorms.entity.Autor;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    boolean existsById(Long id);
    boolean existByNome(String titulo);
    Optional<Autor> findByNome(String titulo);

}
