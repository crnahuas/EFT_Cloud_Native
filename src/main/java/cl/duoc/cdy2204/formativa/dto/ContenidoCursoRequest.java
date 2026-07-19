package cl.duoc.cdy2204.formativa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ContenidoCursoRequest {

    @NotBlank
    @Size(max = 160)
    private String titulo;

    @NotBlank
    @Size(max = 500)
    private String descripcion;

    @NotBlank
    @Size(max = 500)
    private String urlContenido;

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
