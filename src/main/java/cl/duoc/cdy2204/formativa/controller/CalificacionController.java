package cl.duoc.cdy2204.formativa.controller;

import cl.duoc.cdy2204.formativa.dto.CalificacionRequest;
import cl.duoc.cdy2204.formativa.dto.CalificacionResponse;
import cl.duoc.cdy2204.formativa.service.CalificacionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/calificaciones")
public class CalificacionController {

    private final CalificacionService calificacionService;

    public CalificacionController(CalificacionService calificacionService) {
        this.calificacionService = calificacionService;
    }

    @GetMapping
    public ResponseEntity<List<CalificacionResponse>> listar(
            @RequestParam @Positive Long inscripcionId
    ) {
        return ResponseEntity.ok(calificacionService.listarPorInscripcion(inscripcionId));
    }

    @PostMapping
    public ResponseEntity<CalificacionResponse> registrar(@Valid @RequestBody CalificacionRequest request) {
        CalificacionResponse response = calificacionService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
