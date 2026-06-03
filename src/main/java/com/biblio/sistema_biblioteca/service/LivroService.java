package com.biblio.sistema_biblioteca.service;

import com.biblio.sistema_biblioteca.entity.Livro;
import com.biblio.sistema_biblioteca.entity.Usuario;
import com.biblio.sistema_biblioteca.exception.LivroException;
import com.biblio.sistema_biblioteca.exception.UsuarioException;
import com.biblio.sistema_biblioteca.repository.EmprestimoRepository;
import com.biblio.sistema_biblioteca.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LivroService {
    private final LivroRepository livroRepository;
    private final EmprestimoRepository emprestimoRepository;

    //salvar livro
    public Livro salvarLivro(Livro livro) {
        if (livroRepository.existsByTituloIgnoreCase(livro.getTitulo())) {
            throw new LivroException("Livro já cadastrado!");
        }

        return livroRepository.save(livro);
    }

    //atualizar livro
    public Livro atualizarLivro(Long id, Livro livroDadosNovos) {
        Livro livroAtual = livroRepository.findById(id)
                .orElseThrow(() -> new LivroException("Livro não encontrado!"));

        livroAtual.setTitulo(livroDadosNovos.getTitulo());
        livroAtual.setIsbn(livroDadosNovos.getIsbn());

        return livroRepository.save(livroAtual);
    }

    //apagar livro
    public void apagarLivro(Long id) {
        if (!livroRepository.existsById(id)) {
            throw new LivroException("Livro não encontrado!");
        }

        if (emprestimoRepository.existsByLivroId(id)) {
            throw new LivroException("O livro não pode ser excluído enquanto estiver emprestrado!");
        }

        livroRepository.deleteById(id);
    }

    //listar livros
    @Transactional(readOnly = true)
    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }

    //listar livros por categoria
    @Transactional(readOnly = true)
    public List<Livro> listarLivroCategoria(String categoria) {
        return livroRepository.findByCategoria(categoria);
    }

    //buscar livro por id
    @Transactional(readOnly = true)
    public Livro buscarLivroId(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new LivroException("Id não encontrado!"));
    }

    //buscar livro por titulo
    @Transactional(readOnly = true)
    public List<Livro> buscarLivro(String titulo) {
        return livroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    //buscar livro por isbn
    @Transactional(readOnly = true)
    public Livro buscarLivroIsbn(String isbn) {
        return livroRepository.findByIsbn(isbn);
    }
}
