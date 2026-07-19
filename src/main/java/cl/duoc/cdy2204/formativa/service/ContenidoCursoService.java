package cl.duoc.cdy2204.formativa.service;

import cl.duoc.cdy2204.formativa.dto.ContenidoCursoRequest;
import cl.duoc.cdy2204.formativa.dto.ContenidoCursoResponse;
import cl.duoc.cdy2204.formativa.entity.ContenidoCurso;
import cl.duoc.cdy2204.formativa.entity.Curso;
import cl.duoc.cdy2204.formativa.exception.RecursoNoEncontradoException;
import cl.duoc.cdy2204.formativa.repository.ContenidoCursoRepository;
import cl.duoc.cdy2204.formativa.repository.CursoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContenidoCursoService {

    private final CursoRepository cursoRepository;
    private final ContenidoCursoRepository contenidoCursoRepository;

    public ContenidoCursoService(
            CursoRepository cursoRepository,
            ContenidoCursoRepository contenidoCursoRepository
    ) {
        this.cursoRepository = cursoRepository;
        this.contenidoCursoRepository = contenidoCursoRepository;
    }

    @Transactional(readOnly = true)
    public List<ContenidoCursoResponse> listarPorCurso(Long cursoId) {
        verificarCurso(cursoId);
        return contenidoCursoRepository.findByCursoIdOrderByIdAsc(cursoId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public ContenidoCursoResponse crear(Long cursoId, ContenidoCursoRequest request) {
        Curso curso = verificarCurso(cursoId);
        ContenidoCurso contenido = new ContenidoCurso();
        contenido.setCurso(curso);
        contenido.setTitulo(request.getTitulo());
        contenido.setDescripcion(request.getDescripcion());
        contenido.setUrlContenido(request.getUrlContenido());

        return toResponse(contenidoCursoRepository.save(contenido));
    }

    private Curso verificarCurso(Long cursoId) {
        return cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el curso " + cursoId));
    }

    private ContenidoCursoResponse toResponse(ContenidoCurso contenido) {
        return new ContenidoCursoResponse(
                contenido.getId(),
                contenido.getCurso().getId(),
                contenido.getTitulo(),
                contenido.getDescripcion(),
                contenido.getUrlContenido()
        );
    }
}
