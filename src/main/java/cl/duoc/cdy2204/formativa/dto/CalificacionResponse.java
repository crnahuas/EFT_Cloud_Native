package cl.duoc.cdy2204.formativa.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CalificacionResponse {

    private Long id;
    private Long inscripcionId;
    private Long examenId;
    private Long cursoId;
    private String estudianteNombre;
    private String examenTitulo;
    private BigDecimal puntaje;
    private BigDecimal puntajeMaximo;
    private String comentario;
    private LocalDateTime fechaRegistro;

    public CalificacionResponse(
            Long id,
            Long inscripcionId,
            Long examenId,
            Long cursoId,
            String estudianteNombre,
            String examenTitulo,
            BigDecimal puntaje,
            BigDecimal puntajeMaximo,
            String comentario,
            LocalDateTime fechaRegistro
    ) {
        this.id = id;
        this.inscripcionId = inscripcionId;
        this.examenId = examenId;
        this.cursoId = cursoId;
        this.estudianteNombre = estudianteNombre;
        this.examenTitulo = examenTitulo;
        this.puntaje = puntaje;
        this.puntajeMaximo = puntajeMaximo;
        this.comentario = comentario;
        this.fechaRegistro = fechaRegistro;
    }

    public Long getId() {
        return id;
    }

    public Long getInscripcionId() {
        return inscripcionId;
    }

    public Long getExamenId() {
        return examenId;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public String getEstudianteNombre() {
        return estudianteNombre;
    }

    public String getExamenTitulo() {
        return examenTitulo;
    }

    public BigDecimal getPuntaje() {
        return puntaje;
    }

    public BigDecimal getPuntajeMaximo() {
        return puntajeMaximo;
    }

    public String getComentario() {
        return comentario;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
}
