package cl.duoc.cdy2204.formativa.service;

import cl.duoc.cdy2204.formativa.dto.ExamenRequest;
import cl.duoc.cdy2204.formativa.dto.ExamenResponse;
import cl.duoc.cdy2204.formativa.entity.Curso;
import cl.duoc.cdy2204.formativa.entity.Examen;
import cl.duoc.cdy2204.formativa.exception.RecursoNoEncontradoException;
import cl.duoc.cdy2204.formativa.repository.CursoRepository;
import cl.duoc.cdy2204.formativa.repository.ExamenRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExamenService {

    private final CursoRepository cursoRepository;
    private final ExamenRepository examenRepository;

    public ExamenService(CursoRepository cursoRepository, ExamenRepository examenRepository) {
        this.cursoRepository = cursoRepository;
        this.examenRepository = examenRepository;
    }

    @Transactional(readOnly = true)
    public List<ExamenResponse> listarPorCurso(Long cursoId) {
        verificarCurso(cursoId);
        return examenRepository.findByCursoIdOrderByFechaDisponibleAsc(cursoId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public ExamenResponse crear(Long cursoId, ExamenRequest request) {
        Curso curso = verificarCurso(cursoId);
        Examen examen = new Examen();
        examen.setCurso(curso);
        examen.setTitulo(request.getTitulo());
        examen.setDescripcion(request.getDescripcion());
        examen.setFechaDisponible(request.getFechaDisponible());
        examen.setPuntajeMaximo(request.getPuntajeMaximo());

        return toResponse(examenRepository.save(examen));
    }

    private Curso verificarCurso(Long cursoId) {
        return cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el curso " + cursoId));
    }

    public ExamenResponse toResponse(Examen examen) {
        return new ExamenResponse(
                examen.getId(),
                examen.getCurso().getId(),
                examen.getTitulo(),
                examen.getDescripcion(),
                examen.getFechaDisponible(),
                examen.getPuntajeMaximo()
        );
    }
}
