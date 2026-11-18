
package br.edu.escola.BiblioTec.service;

import br.edu.escola.BiblioTec.model.Livro;
import br.edu.escola.BiblioTec.repository.LivroRepository;
import br.edu.escola.BiblioTec.repository.CategoriaRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final CategoriaRepository categoriaRepository;

    public LivroService(
            LivroRepository livroRepository,
            CategoriaRepository categoriaRepository) {

        this.livroRepository = livroRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Livro buscarPorId(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Livro não encontrado: " + id));
    }

    public Livro salvar(Livro livro) {

        if (livro.getCategoria() == null ||
                livro.getCategoria().getId() == null) {
            throw new IllegalArgumentException("Selecione uma categoria.");
        }

        if (!categoriaRepository.existsById(livro.getCategoria().getId())) {
            throw new IllegalArgumentException("Categoria não encontrada.");
        }

        if (livro.getId() == null) {
            if (livroRepository.existsByIsbn(livro.getIsbn())) {
                throw new IllegalArgumentException("Este ISBN já está cadastrado.");
            }

            livro.setQuantidadeDisponivel(livro.getQuantidadeTotal());

        } else {
            Livro existente = buscarPorId(livro.getId());

            boolean isbnDuplicado = livroRepository.findByIsbn(livro.getIsbn())
                    .filter(outro -> !outro.getId().equals(livro.getId()))
                    .isPresent();

            if (isbnDuplicado) {
                throw new IllegalArgumentException("Este ISBN já está cadastrado.");
            }

            int emprestados = existente.getQuantidadeTotal()
                    - existente.getQuantidadeDisponivel();

            if (livro.getQuantidadeTotal() < emprestados) {
                throw new IllegalArgumentException(
                        "A quantidade total não pode ser menor que os livros emprestados."
                );
            }

            livro.setQuantidadeDisponivel(
                    livro.getQuantidadeTotal() - emprestados
            );
        }

        return livroRepository.save(livro);
    }

    public void excluir(Long id) {
        Livro livro = buscarPorId(id);
        livroRepository.delete(livro);
    }

    public List<Livro> pesquisarPorTitulo(String titulo) {

        if (titulo == null || titulo.isBlank()) {
            return livroRepository.findAll();
        }

        return livroRepository.findByTituloContainingIgnoreCaseOrderByTituloAsc(
                titulo.trim()
        );
    }
}
