package br.edu.escola.BiblioTec.repository;

import br.edu.escola.BiblioTec.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}