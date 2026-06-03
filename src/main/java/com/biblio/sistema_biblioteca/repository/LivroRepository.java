package com.biblio.sistema_biblioteca.repository;

import com.biblio.sistema_biblioteca.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
    boolean existsByTituloIgnoreCase(String titulo);
    boolean existsByAutorId(Long id);
    boolean existsByCategoriaId(Long id);
    List<Livro> findByCategoria(String categoria);
    Livro findByIsbn(String isbn);
}
