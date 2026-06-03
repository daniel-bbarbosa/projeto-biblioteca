package com.biblio.sistema_biblioteca.controller;

import com.biblio.sistema_biblioteca.entity.Autor;
import com.biblio.sistema_biblioteca.entity.Usuario;
import com.biblio.sistema_biblioteca.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/autores")
@RequiredArgsConstructor
public class AutorController {
    private final AutorService autorService;

    //listar autores
    @GetMapping
    public ResponseEntity<List<Autor>> listarTodos() {
        return ResponseEntity.ok(autorService.listarAutores());
    }

    //procurar autor por id
    @GetMapping("/{id}")
    public ResponseEntity<Autor> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(autorService.buscarAutorId(id));
    }

    //procurar autor por nome
    @GetMapping("/buscar")
    public ResponseEntity<List<Autor>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(autorService.buscarAutor(nome));
    }

    //cadastrar autor
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Autor cadastrar(@Valid @RequestBody Autor autor) {
        return autorService.cadastrarAutor(autor);
    }

    // atualizar autor existente
    @PutMapping("/{id}")
    public ResponseEntity<Autor> atualizar(@PathVariable Long id, @Valid @RequestBody Autor autorDadosNovos) {
        return ResponseEntity.ok(autorService.atualizarAutor(id, autorDadosNovos));
    }

    //deletar autor
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        autorService.apagarAutor(id);
    }
}
