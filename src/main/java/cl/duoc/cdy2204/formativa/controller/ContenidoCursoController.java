package cl.duoc.cdy2204.formativa.controller;

import cl.duoc.cdy2204.formativa.dto.ContenidoCursoRequest;
import cl.duoc.cdy2204.formativa.dto.ContenidoCursoResponse;
import cl.duoc.cdy2204.formativa.service.ContenidoCursoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/cursos/{cursoId}/contenidos")
public class ContenidoCursoController {

    private final ContenidoCursoService contenidoCursoService;

    public ContenidoCursoController(ContenidoCursoService contenidoCursoService) {
        this.contenidoCursoService = contenidoCursoService;
    }

    @GetMapping
    public ResponseEntity<List<ContenidoCursoResponse>> listar(@PathVariable @Positive Long cursoId) {
        return ResponseEntity.ok(contenidoCursoService.listarPorCurso(cursoId));
    }

    @PostMapping
    public ResponseEntity<ContenidoCursoResponse> crear(
            @PathVariable @Positive Long cursoId,
            @Valid @RequestBody ContenidoCursoRequest request
    ) {
        ContenidoCursoResponse response = contenidoCursoService.crear(cursoId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
