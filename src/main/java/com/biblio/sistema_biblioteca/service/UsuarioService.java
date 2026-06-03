package com.biblio.sistema_biblioteca.service;

import com.biblio.sistema_biblioteca.entity.Usuario;
import com.biblio.sistema_biblioteca.exception.UsuarioException;
import com.biblio.sistema_biblioteca.repository.AutorRepository;
import com.biblio.sistema_biblioteca.repository.EmprestimoRepository;
import com.biblio.sistema_biblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmprestimoRepository emprestimoRepository;
    private final AutorRepository autorRepository;

    //cadastrar usuario
    public Usuario cadastrarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmailIgnoreCase(usuario.getEmail())) {
            throw new UsuarioException("E-mail já cadastrado!");
        }

        String senhaHash = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaHash);

        return usuarioRepository.save(usuario);
    }

    //atualizar usuario
    public Usuario atualizarUsuario(Long id, Usuario usuarioDadosNovos) {
        Usuario usuarioAtual = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioException("Usuário não encontrado!"));

        usuarioAtual.setNome(usuarioDadosNovos.getNome());
        usuarioAtual.setEmail(usuarioDadosNovos.getEmail());
        usuarioAtual.setSenha(usuarioDadosNovos.getSenha());

        return usuarioRepository.save(usuarioAtual);
    }

    //apagar usuario
    public void apagarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new UsuarioException("Usuário não encontrado!");
        }

        if (emprestimoRepository.existsByUsuarioId(id)) {
            throw new UsuarioException("Não é possível apagar um usuário com empréstimo ativo!");
        }

        usuarioRepository.deleteById(id);
    }

    //listar usuarios
    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    //buscar usuario por nome
    @Transactional(readOnly = true)
    public List<Usuario> buscarUsuarioNome(String nome) {
        return usuarioRepository.findByNomeContainingIgnoreCase(nome);
    }

    //buscar usuario por email
    @Transactional(readOnly = true)
    public Usuario buscarUsuarioEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    //buscar usuario por id
    @Transactional(readOnly = true)
    public Usuario buscarUsuarioId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioException("Usuário não encontrado!"));
    }
}