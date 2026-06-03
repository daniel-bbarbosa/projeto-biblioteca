package com.biblio.sistema_biblioteca.controller;

import com.biblio.sistema_biblioteca.entity.Usuario;
import com.biblio.sistema_biblioteca.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    //listar autores
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    //procurar usuario por id
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioId(id));
    }

    //procurar usuario por nome
    @GetMapping("/buscar-nome")
    public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioNome(nome));
    }

    //procurar usuario por email
    @GetMapping("/buscar-email")
    public ResponseEntity<Usuario> buscarPorEmail(@RequestParam String email) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioEmail(email));
    }

    //cadastrar usuario
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario cadastar(@Valid @RequestBody Usuario usuario) {
        return usuarioService.cadastrarUsuario(usuario);
    }

    // atualizar usuário existente
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @Valid @RequestBody Usuario usuarioDadosNovos) {
        return ResponseEntity.ok(usuarioService.atualizarUsuario(id, usuarioDadosNovos));
    }

    //apagar usuario
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        usuarioService.apagarUsuario(id);
    }
}
