
package br.edu.escola.BiblioTec.service;

import br.edu.escola.BiblioTec.model.Categoria;
import br.edu.escola.BiblioTec.repository.CategoriaRepository;
import br.edu.escola.BiblioTec.repository.LivroRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final LivroRepository livroRepository;

    public CategoriaService(
            CategoriaRepository categoriaRepository,
            LivroRepository livroRepository) {

        this.categoriaRepository = categoriaRepository;
        this.livroRepository = livroRepository;
    }

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public Categoria salvar(Categoria categoria) {

        String nome = categoria.getNome().trim();

        Optional<Categoria> existente =
                categoriaRepository.findByNomeIgnoreCase(nome);

        if (existente.isPresent()) {
            boolean mesmaCategoria =
                    categoria.getId() != null &&
                            existente.get().getId().equals(categoria.getId());

            if (!mesmaCategoria) {
                throw new IllegalArgumentException(
                        "Essa categoria já está cadastrada!"
                );
            }
        }

        categoria.setNome(nome);

        return categoriaRepository.save(categoria);
    }

    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Categoria não encontrada: " + id
                        ));
    }

    public void excluir(Long id) {

        Categoria categoria = buscarPorId(id);

        if (livroRepository.possuiLivrosNaCategoria(id)) {
            throw new IllegalArgumentException(
                    "Não é possível excluir esta categoria porque existem livros vinculados a ela."
            );
        }

        categoriaRepository.delete(categoria);
    }
}
