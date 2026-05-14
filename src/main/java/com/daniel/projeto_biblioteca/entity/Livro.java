package com.daniel.projeto_biblioteca.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    
    private String isbn;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;
}
