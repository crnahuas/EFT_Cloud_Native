package cl.duoc.cdy2204.formativa.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ExamenResponse {

    private Long id;
    private Long cursoId;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaDisponible;
    private BigDecimal puntajeMaximo;

    public ExamenResponse(
            Long id,
            Long cursoId,
            String titulo,
            String descripcion,
            LocalDateTime fechaDisponible,
            BigDecimal puntajeMaximo
    ) {
        this.id = id;
        this.cursoId = cursoId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaDisponible = fechaDisponible;
        this.puntajeMaximo = puntajeMaximo;
    }

    public Long getId() {
        return id;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDateTime getFechaDisponible() {
        return fechaDisponible;
    }

    public BigDecimal getPuntajeMaximo() {
        return puntajeMaximo;
    }
}
