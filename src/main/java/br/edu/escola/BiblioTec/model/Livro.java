
package br.edu.escola.BiblioTec.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "livro")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    @NotBlank(message = "O título é obrigatório")
    @Size(max = 150)
    private String titulo;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "O autor é obrigatório")
    @Size(max = 100)
    private String autor;

    @Column(nullable = false, unique = true, length = 13)
    @NotBlank(message = "O ISBN é obrigatório")
    @Pattern(regexp = "\\d{13}",
            message = "O ISBN deve ter exatamente 13 dígitos")
    private String isbn;

    @Column(name = "ano_publicacao")
    @Positive(message = "O ano deve ser positivo")
    private Integer anoPublicacao;

    @Column(name = "quantidade_total", nullable = false)
    @NotNull(message = "Informe a quantidade")
    @Min(value = 1, message = "A quantidade mínima é 1")
    private Integer quantidadeTotal;

    @Column(name = "quantidade_disponivel", nullable = false)
    private Integer quantidadeDisponivel;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    public Livro() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Integer anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public Integer getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(Integer quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }

    public Integer getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(Integer quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
// Gere os getters e setters pelo IntelliJ:
    // Alt + Insert → Getter and Setter → Select All
}
