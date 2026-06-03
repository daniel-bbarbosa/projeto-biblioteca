package com.biblio.sistema_biblioteca.repository;

import com.biblio.sistema_biblioteca.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByNomeContainingIgnoreCase(String nome);
    List<Usuario> findByEmailContainingIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
    Usuario findByEmail(String email);
}
