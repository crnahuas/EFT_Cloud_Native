package cl.duoc.cdy2204.formativa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "CONTENIDOS_CURSO")
public class ContenidoCurso {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contenido_curso_seq")
    @SequenceGenerator(name = "contenido_curso_seq", sequenceName = "CONTENIDO_CURSO_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CURSO_ID", nullable = false)
    private Curso curso;

    @Column(nullable = false, length = 160)
    private String titulo;

    @Column(nullable = false, length = 500)
    private String descripcion;

    @Column(name = "URL_CONTENIDO", nullable = false, length = 500)
    private String urlContenido;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlContenido() {
        return urlContenido;
    }

    public void setUrlContenido(String urlContenido) {
        this.urlContenido = urlContenido;
    }
}
