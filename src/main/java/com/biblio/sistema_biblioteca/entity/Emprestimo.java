package com.biblio.sistema_biblioteca.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "emprestimo")
@NoArgsConstructor
@Getter @Setter
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private LocalDate dataEmprestimo;

    @NotNull
    @Column(nullable = false)
    private LocalDate dataDevolucao;

    @NotNull
    @Column(nullable = false)
    private LocalDate dataDevolucaoReal;

    @NotNull
    @Column(nullable = false)
    private double multa;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    public Emprestimo(Long id, LocalDate dataEmprestimo, LocalDate dataDevolucao, Usuario usuario, Livro livro) {
        this.id = id;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.usuario = usuario;
        this.livro = livro;
    }
}