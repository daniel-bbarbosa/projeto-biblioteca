package com.biblio.sistema_biblioteca.controller;

import com.biblio.sistema_biblioteca.entity.Categoria;
import com.biblio.sistema_biblioteca.entity.Usuario;
import com.biblio.sistema_biblioteca.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
@RequiredArgsConstructor
public class CategoriaController {
    private final CategoriaService categoriaService;

    //listar categorias
    @GetMapping
    public ResponseEntity<List<Categoria>> listarTodas() {
        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

    //procurar categoria por id
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }

    //procurar categoria pelo nome
    @GetMapping("/buscar")
    public ResponseEntity<List<Categoria>> buscarPeloNome(@RequestParam String nome) {
        return ResponseEntity.ok(categoriaService.buscarCategoria(nome));
    }

    //cadastrar categoria
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Categoria cadastrar(@Valid @RequestBody Categoria categoria) {
        return categoriaService.cadastrarCategoria(categoria);
    }

    // atualizar categoria existente
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> atualizar(@PathVariable Long id, @Valid @RequestBody Categoria categoriaDadosNovos) {
        return ResponseEntity.ok(categoriaService.atualizarCategoria(id, categoriaDadosNovos));
    }

    //deletar categoria
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        categoriaService.apagarCategoria(id);
    }
}
