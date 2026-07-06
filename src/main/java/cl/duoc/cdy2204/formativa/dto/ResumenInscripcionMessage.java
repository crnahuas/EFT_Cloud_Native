package cl.duoc.cdy2204.formativa.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ResumenInscripcionMessage {

    private Long numeroResumen;
    private String estudianteNombre;
    private String estudianteEmail;
    private LocalDateTime fechaInscripcion;
    private BigDecimal total;
    private String contenidoResumen;

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
}
