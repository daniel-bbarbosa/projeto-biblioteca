package com.biblio.sistema_biblioteca.controller;

import com.biblio.sistema_biblioteca.entity.Emprestimo;
import com.biblio.sistema_biblioteca.service.EmprestimoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/emprestimos")
@RequiredArgsConstructor
public class EmprestimoController {
    private final EmprestimoService emprestimoService;

    //listar emprestimos
    @GetMapping
    public ResponseEntity<List<Emprestimo>> listarTodos() {
        return ResponseEntity.ok(emprestimoService.listarEmprestimos());
    }

    //buscar emprestimo por id
    @GetMapping("/{id}")
    public ResponseEntity<Emprestimo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(emprestimoService.buscarEmprestimoId(id));
    }

    //buscar emprestimo dentro de um tempo
    @GetMapping("/buscar-por-datas")
    public ResponseEntity<List<Emprestimo>> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        return ResponseEntity.ok(emprestimoService.buscarEmprestimosPorPeriodo(inicio, fim));
    }

    //fazer emprestimo
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Emprestimo fazerEmprestimo(@Valid @RequestBody Emprestimo emprestimo) {
        return emprestimoService.fazerEmprestimo(emprestimo);
    }

    //devolver livro
    @PutMapping("/{id}/devolver")
    public ResponseEntity<Emprestimo> devolverLivro(@PathVariable Long id) {
        return ResponseEntity.ok(emprestimoService.devolverLivro(id));
    }
}
