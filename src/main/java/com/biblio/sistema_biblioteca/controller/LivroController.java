package com.biblio.sistema_biblioteca.controller;

import com.biblio.sistema_biblioteca.entity.Livro;
import com.biblio.sistema_biblioteca.entity.Usuario;
import com.biblio.sistema_biblioteca.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/livros")
@RequiredArgsConstructor
public class LivroController {
    private final LivroService livroService;

    //listar livros
    @GetMapping
    public ResponseEntity<List<Livro>> listarTodos() {
        return ResponseEntity.ok(livroService.listarLivros());
    }

    //buscar livro por id
    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(livroService.buscarLivroId(id));
    }

    //buscar livro por nome
    @GetMapping("/buscar-nome")
    public ResponseEntity<List<Livro>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(livroService.buscarLivro(nome));
    }

    //buscar livro por isbn
    @GetMapping("/buscar-isbn")
    public ResponseEntity<Livro> buscarPorIsbn(@RequestParam String isbn) {
        return ResponseEntity.ok(livroService.buscarLivroIsbn(isbn));
    }

    //cadastrar livro
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Livro cadastrar(@Valid @RequestBody Livro livro) {
        return livroService.salvarLivro(livro);
    }

    // atualizar livro existente
    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizar(@PathVariable Long id, @Valid @RequestBody Livro livroDadosNovos) {
        return ResponseEntity.ok(livroService.atualizarLivro(id, livroDadosNovos));
    }

    //apagar livro
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        livroService.apagarLivro(id);
    }
}
