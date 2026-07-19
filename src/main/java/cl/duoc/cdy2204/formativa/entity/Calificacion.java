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
@Table(name = "CALIFICACIONES")
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "calificacion_seq")
    @SequenceGenerator(name = "calificacion_seq", sequenceName = "CALIFICACION_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "INSCRIPCION_ID", nullable = false)
    private Inscripcion inscripcion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "EXAMEN_ID", nullable = false)
    private Examen examen;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal puntaje;

    @Column(length = 500)
    private String comentario;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Inscripcion getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(Inscripcion inscripcion) {
        this.inscripcion = inscripcion;
    }

    public Examen getExamen() {
        return examen;
    }

    public void setExamen(Examen examen) {
        this.examen = examen;
    }

    public BigDecimal getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(BigDecimal puntaje) {
        this.puntaje = puntaje;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
