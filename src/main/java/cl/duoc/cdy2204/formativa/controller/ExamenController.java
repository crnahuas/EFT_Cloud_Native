package cl.duoc.cdy2204.formativa.controller;

import cl.duoc.cdy2204.formativa.dto.ExamenRequest;
import cl.duoc.cdy2204.formativa.dto.ExamenResponse;
import cl.duoc.cdy2204.formativa.service.ExamenService;
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
@RequestMapping("/cursos/{cursoId}/examenes")
public class ExamenController {

    private final ExamenService examenService;

    public ExamenController(ExamenService examenService) {
        this.examenService = examenService;
    }

    @GetMapping
    public ResponseEntity<List<ExamenResponse>> listar(@PathVariable @Positive Long cursoId) {
        return ResponseEntity.ok(examenService.listarPorCurso(cursoId));
    }

    @PostMapping
    public ResponseEntity<ExamenResponse> crear(
            @PathVariable @Positive Long cursoId,
            @Valid @RequestBody ExamenRequest request
    ) {
        ExamenResponse response = examenService.crear(cursoId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
