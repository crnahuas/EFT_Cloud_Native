package cl.duoc.cdy2204.formativa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "RESUMENES_INSCRIPCION_MQ")
public class ResumenInscripcionMq {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resumen_mq_seq")
    @SequenceGenerator(name = "resumen_mq_seq", sequenceName = "RESUMEN_MQ_SEQ", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private Long numeroResumen;

    @Column(nullable = false, length = 120)
    private String estudianteNombre;

    @Column(nullable = false, length = 120)
    private String estudianteEmail;

    @Column(nullable = false)
    private LocalDateTime fechaInscripcion;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal total;

    @Lob
    @Column(nullable = false)
    private String contenidoResumen;

    @Column(nullable = false)
    private LocalDateTime fechaProcesamiento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroResumen() {
        return numeroResumen;
    }

    public void setNumeroResumen(Long numeroResumen) {
        this.numeroResumen = numeroResumen;
    }

    public String getEstudianteNombre() {
        return estudianteNombre;
    }

    public void setEstudianteNombre(String estudianteNombre) {
        this.estudianteNombre = estudianteNombre;
    }

    public String getEstudianteEmail() {
        return estudianteEmail;
    }

    public void setEstudianteEmail(String estudianteEmail) {
        this.estudianteEmail = estudianteEmail;
    }

    public LocalDateTime getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDateTime fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getContenidoResumen() {
        return contenidoResumen;
    }

    public void setContenidoResumen(String contenidoResumen) {
        this.contenidoResumen = contenidoResumen;
    }

    public LocalDateTime getFechaProcesamiento() {
        return fechaProcesamiento;
    }

    public void setFechaProcesamiento(LocalDateTime fechaProcesamiento) {
        this.fechaProcesamiento = fechaProcesamiento;
    }
}
