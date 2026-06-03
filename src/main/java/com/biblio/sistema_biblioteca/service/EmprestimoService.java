package com.biblio.sistema_biblioteca.service;

import com.biblio.sistema_biblioteca.entity.Emprestimo;
import com.biblio.sistema_biblioteca.exception.EmprestimoException;
import com.biblio.sistema_biblioteca.repository.EmprestimoRepository;
import com.biblio.sistema_biblioteca.repository.LivroRepository;
import com.biblio.sistema_biblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EmprestimoService {
    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;

    //fazer emprestimo
    public Emprestimo fazerEmprestimo(Emprestimo emprestimo) {
        //usuario existe
        if (!usuarioRepository.existsById(emprestimo.getUsuario().getId())) {
            throw new EmprestimoException("Usuário não encontrado!");
        }

        //livro existe
        if (!livroRepository.existsById(emprestimo.getLivro().getId())) {
            throw new EmprestimoException("Livro não encontrado!");
        }

        //livro emprestado
        if (emprestimoRepository.existsByLivroIdAndDataDevolucaoRealIsNull(emprestimo.getLivro().getId())) {
            throw new EmprestimoException("Este livro já está emprestado no momento!");
        }

        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setDataDevolucao(LocalDate.now().plusDays(15));
        emprestimo.setDataDevolucaoReal(null);
        emprestimo.setMulta(0.0);

        return emprestimoRepository.save(emprestimo);
    }

    //fazer devolucao
    public Emprestimo devolverLivro(Long id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new EmprestimoException("Empréstimo não encontrado!"));

        LocalDate dataEntrega = LocalDate.now();

        if (dataEntrega.isAfter(emprestimo.getDataDevolucao())) {
            long diasAtraso = ChronoUnit.DAYS.between(emprestimo.getDataDevolucao(), dataEntrega);

            double valorPorDia = 3.00;
            emprestimo.setMulta(diasAtraso * valorPorDia);
        } else {
            emprestimo.setMulta(0.0);
        }

        return emprestimoRepository.save(emprestimo);
    }

    //listar emprestimos
    @Transactional(readOnly = true)
    public List<Emprestimo> listarEmprestimos() {
        return emprestimoRepository.findAll();
    }

    //buscar emprestimo por id
    @Transactional(readOnly = true)
    public Emprestimo buscarEmprestimoId(Long id) {
        return emprestimoRepository.findById(id)
                .orElseThrow(() -> new EmprestimoException(("Empréstimo não encontrado!")));
    }

    //buscar emprestimo por data
    @Transactional(readOnly = true)
    public List<Emprestimo> buscarEmprestimosPorPeriodo(LocalDate inicio, LocalDate fim) {
        return emprestimoRepository.findByDataEmprestimoBetween(inicio, fim);
    }
}
