package com.biblio.sistema_biblioteca.service;

import com.biblio.sistema_biblioteca.entity.Categoria;
import com.biblio.sistema_biblioteca.entity.Usuario;
import com.biblio.sistema_biblioteca.exception.CategoriaException;
import com.biblio.sistema_biblioteca.repository.CategoriaRepository;
import com.biblio.sistema_biblioteca.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final LivroRepository livroRepository;

    //criar categoria
    public Categoria cadastrarCategoria(Categoria categoria) {
        if (categoriaRepository.existsByNomeIgnoreCase(categoria.getNome())) {
            throw new CategoriaException("Categoria já registrada");
        }

        return categoriaRepository.save(categoria);
    }

    //atualizar categoria
    public Categoria atualizarCategoria(Long id, Categoria categoriaDadosNovos) {
        Categoria categoriaAtual = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaException("Categoria não encontrada!"));

        categoriaAtual.setNome(categoriaDadosNovos.getNome());

        return categoriaRepository.save(categoriaAtual);
    }

    //apagar categoria
    public void apagarCategoria(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new CategoriaException("Categoria não encontrada!");
        }

        if (livroRepository.existsByCategoriaId(id)) {
            throw new CategoriaException("Não é possível apagar uma categoria com livro(s) cadastrado(s)");
        }

        categoriaRepository.deleteById(id);
    }

    //listar categorias
    @Transactional(readOnly = true)
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    //buscar categoria
    @Transactional(readOnly = true)
    public List<Categoria> buscarCategoria(String nome) {
        return categoriaRepository.findByNomeContainingIgnoreCase(nome);
    }

    //buscar categoria por id
    @Transactional(readOnly = true)
    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaException("Categoria não encontrada!"));
    }
}
