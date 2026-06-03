package com.biblio.sistema_biblioteca.service;

import com.biblio.sistema_biblioteca.entity.Autor;
import com.biblio.sistema_biblioteca.entity.Usuario;
import com.biblio.sistema_biblioteca.exception.AutorException;
import com.biblio.sistema_biblioteca.exception.UsuarioException;
import com.biblio.sistema_biblioteca.repository.AutorRepository;
import com.biblio.sistema_biblioteca.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AutorService {
    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;

    //salvar autor
    public Autor cadastrarAutor(Autor autor) {
        if (autorRepository.existsByNomeIgnoreCase(autor.getNome())) {
            throw new AutorException("Autor já cadastrado!");
        }

        return autorRepository.save(autor);
    }

    //atualizar autor
    public Autor atualizarAutor(Long id, Autor autorDadosNovos) {
        Autor autorAtual = autorRepository.findById(id)
                .orElseThrow(() -> new AutorException("Autor não encontrado com o ID!"));

        autorAtual.setNome(autorDadosNovos.getNome());
        autorAtual.setNacionalidade(autorDadosNovos.getNacionalidade());

        return autorRepository.save(autorAtual);
    }

    //apagar autor
    public void apagarAutor(Long id) {
        if (!autorRepository.existsById(id)) {
            throw new AutorException("Autor não encontrado!");
        }

        if (livroRepository.existsByAutorId(id)) {
            throw new AutorException("Não é possível apagar um autor com livro(s) cadastrado(s)");
        }

        autorRepository.deleteById(id);
    }

    //listar autores
    @Transactional(readOnly = true)
    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }

    //buscar autor por nome
    @Transactional(readOnly = true)
    public List<Autor> buscarAutor(String nome) {
        return autorRepository.findByNomeContainingIgnoreCase(nome);
    }

    //buscar autor por id
    @Transactional(readOnly = true)
    public Autor buscarAutorId(Long id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new AutorException("Autor não encontrado!"));
    }
}
