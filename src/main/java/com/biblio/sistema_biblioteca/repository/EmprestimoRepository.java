package com.biblio.sistema_biblioteca.repository;

import com.biblio.sistema_biblioteca.entity.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    List<Emprestimo> findByDataEmprestimo(LocalDate dataEmprestimo);
    List<Emprestimo> findByDataDevolucao(LocalDate dataDevolucao);
    List<Emprestimo> findByDataEmprestimoBefore(LocalDate dataDevolucao);
    List<Emprestimo> findByDataEmprestimoBetween(LocalDate dataEmprestimo, LocalDate dataDevolucao);
    boolean existsByLivroId(Long id);
    boolean existsByUsuarioId(Long id);
    boolean existsByLivroIdAndDataDevolucaoRealIsNull(Long id);
}