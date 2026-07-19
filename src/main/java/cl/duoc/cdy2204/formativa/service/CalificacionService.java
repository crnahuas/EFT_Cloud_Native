package cl.duoc.cdy2204.formativa.service;

import cl.duoc.cdy2204.formativa.dto.CalificacionRequest;
import cl.duoc.cdy2204.formativa.dto.CalificacionResponse;
import cl.duoc.cdy2204.formativa.entity.Calificacion;
import cl.duoc.cdy2204.formativa.entity.Examen;
import cl.duoc.cdy2204.formativa.entity.Inscripcion;
import cl.duoc.cdy2204.formativa.exception.RecursoNoEncontradoException;
import cl.duoc.cdy2204.formativa.repository.CalificacionRepository;
import cl.duoc.cdy2204.formativa.repository.ExamenRepository;
import cl.duoc.cdy2204.formativa.repository.InscripcionRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CalificacionService {

    private final CalificacionRepository calificacionRepository;
    private final InscripcionRepository inscripcionRepository;
    private final ExamenRepository examenRepository;

    public CalificacionService(
            CalificacionRepository calificacionRepository,
            InscripcionRepository inscripcionRepository,
            ExamenRepository examenRepository
    ) {
        this.calificacionRepository = calificacionRepository;
        this.inscripcionRepository = inscripcionRepository;
        this.examenRepository = examenRepository;
    }

    @Transactional(readOnly = true)
    public List<CalificacionResponse> listarPorInscripcion(Long inscripcionId) {
        verificarInscripcion(inscripcionId);
        return calificacionRepository.findByInscripcionIdOrderByFechaRegistroDesc(inscripcionId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public CalificacionResponse registrar(CalificacionRequest request) {
        Inscripcion inscripcion = verificarInscripcion(request.getInscripcionId());
        Examen examen = examenRepository.findById(request.getExamenId())
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el examen " + request.getExamenId()));
        validarCalificacion(inscripcion, examen, request);

        Calificacion calificacion = new Calificacion();
        calificacion.setInscripcion(inscripcion);
        calificacion.setExamen(examen);
        calificacion.setPuntaje(request.getPuntaje());
        calificacion.setComentario(request.getComentario());
        calificacion.setFechaRegistro(LocalDateTime.now());

        return toResponse(calificacionRepository.save(calificacion));
    }

    private Inscripcion verificarInscripcion(Long inscripcionId) {
        return inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la inscripcion " + inscripcionId));
    }

    private void validarCalificacion(Inscripcion inscripcion, Examen examen, CalificacionRequest request) {
        Long cursoExamenId = examen.getCurso().getId();
        boolean cursoInscrito = inscripcion.getCursos().stream()
                .anyMatch(curso -> curso.getId().equals(cursoExamenId));
        if (!cursoInscrito) {
            throw new IllegalArgumentException(
                    "La inscripcion no pertenece al curso del examen " + request.getExamenId()
            );
        }
        if (request.getPuntaje().compareTo(examen.getPuntajeMaximo()) > 0) {
            throw new IllegalArgumentException("El puntaje no puede superar el maximo del examen");
        }
    }

    private CalificacionResponse toResponse(Calificacion calificacion) {
        return new CalificacionResponse(
                calificacion.getId(),
                calificacion.getInscripcion().getId(),
                calificacion.getExamen().getId(),
                calificacion.getExamen().getCurso().getId(),
                calificacion.getInscripcion().getEstudianteNombre(),
                calificacion.getExamen().getTitulo(),
                calificacion.getPuntaje(),
                calificacion.getExamen().getPuntajeMaximo(),
                calificacion.getComentario(),
                calificacion.getFechaRegistro()
        );
    }
}
