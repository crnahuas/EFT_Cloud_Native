package cl.duoc.cdy2204.formativa.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ExamenRequest {

    @NotBlank
    @Size(max = 160)
    private String titulo;

    @NotBlank
    @Size(max = 500)
    private String descripcion;

    @NotNull
    private LocalDateTime fechaDisponible;

    @NotNull
    @DecimalMin(value = "1.0")
    private BigDecimal puntajeMaximo;

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
