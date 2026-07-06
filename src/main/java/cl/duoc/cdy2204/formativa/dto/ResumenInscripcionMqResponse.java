package cl.duoc.cdy2204.formativa.dto;

import java.time.LocalDateTime;

public class ResumenInscripcionMqResponse {

    private Long id;
    private Long numeroResumen;
    private String estudianteNombre;
    private String estudianteEmail;
    private LocalDateTime fechaInscripcion;
    private LocalDateTime fechaProcesamiento;

    public ResumenInscripcionMqResponse(
            Long id,
            Long numeroResumen,
            String estudianteNombre,
            String estudianteEmail,
            LocalDateTime fechaInscripcion,
            LocalDateTime fechaProcesamiento
    ) {
        this.id = id;
        this.numeroResumen = numeroResumen;
        this.estudianteNombre = estudianteNombre;
        this.estudianteEmail = estudianteEmail;
        this.fechaInscripcion = fechaInscripcion;
        this.fechaProcesamiento = fechaProcesamiento;
    }

    public Long getId() {
        return id;
    }

    public Long getNumeroResumen() {
        return numeroResumen;
    }

    public String getEstudianteNombre() {
        return estudianteNombre;
    }

    public String getEstudianteEmail() {
        return estudianteEmail;
    }

    public LocalDateTime getFechaInscripcion() {
        return fechaInscripcion;
    }

    public LocalDateTime getFechaProcesamiento() {
        return fechaProcesamiento;
    }
}
