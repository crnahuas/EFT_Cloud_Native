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
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "EXAMENES")
public class Examen {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "examen_seq")
    @SequenceGenerator(name = "examen_seq", sequenceName = "EXAMEN_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CURSO_ID", nullable = false)
    private Curso curso;

    @Column(nullable = false, length = 160)
    private String titulo;

    @Column(nullable = false, length = 500)
    private String descripcion;

    @Column(nullable = false)
    private LocalDateTime fechaDisponible;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal puntajeMaximo;

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

    public LocalDateTime getFechaDisponible() {
        return fechaDisponible;
    }

    public void setFechaDisponible(LocalDateTime fechaDisponible) {
        this.fechaDisponible = fechaDisponible;
    }

    public BigDecimal getPuntajeMaximo() {
        return puntajeMaximo;
    }

    public void setPuntajeMaximo(BigDecimal puntajeMaximo) {
        this.puntajeMaximo = puntajeMaximo;
    }
}
