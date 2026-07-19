package cl.duoc.cdy2204.formativa.dto;

public class ContenidoCursoResponse {

    private Long id;
    private Long cursoId;
    private String titulo;
    private String descripcion;
    private String urlContenido;

    public ContenidoCursoResponse(Long id, Long cursoId, String titulo, String descripcion, String urlContenido) {
        this.id = id;
        this.cursoId = cursoId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.urlContenido = urlContenido;
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

    public String getUrlContenido() {
        return urlContenido;
    }
}
