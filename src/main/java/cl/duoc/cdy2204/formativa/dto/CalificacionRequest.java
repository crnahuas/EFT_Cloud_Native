package cl.duoc.cdy2204.formativa.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class CalificacionRequest {

    @NotNull
    private Long inscripcionId;

    @NotNull
    private Long examenId;

    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal puntaje;

    @Size(max = 500)
    private String comentario;

    public Long getInscripcionId() {
        return inscripcionId;
    }

    public void setInscripcionId(Long inscripcionId) {
        this.inscripcionId = inscripcionId;
    }

    public Long getExamenId() {
        return examenId;
    }

    public void setExamenId(Long examenId) {
        this.examenId = examenId;
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
}
