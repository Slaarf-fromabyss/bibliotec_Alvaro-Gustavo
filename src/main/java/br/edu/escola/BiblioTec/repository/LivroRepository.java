
package br.edu.escola.BiblioTec.repository;

import br.edu.escola.BiblioTec.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findByTituloContainingIgnoreCaseOrderByTituloAsc(String titulo);

    Optional<Livro> findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);

    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END " +
            "FROM Livro l WHERE l.categoria.id = :categoriaId")
    boolean possuiLivrosNaCategoria(@Param("categoriaId") Long categoriaId);
}
