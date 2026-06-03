package com.biblio.sistema_biblioteca.repository;

import com.biblio.sistema_biblioteca.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByNomeContainingIgnoreCase(String nome);
    List<Autor> findByNacionalidadeContainingIgnoreCase(String nacionalidade);
    boolean existsByNomeIgnoreCase(String nome);
}
