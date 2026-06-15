package com.biblioteca.livroms.repository;

import com.biblioteca.livroms.entity.Livro;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    boolean existsById(Long id);
    Optional<Livro> findByTitulo(String titulo);
    List<Livro> findByDisponivel(Boolean disponivel);
}
