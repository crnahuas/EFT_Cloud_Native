package cl.duoc.cdy2204.formativa.controller;

import cl.duoc.cdy2204.formativa.dto.InscripcionRequest;
import cl.duoc.cdy2204.formativa.dto.InscripcionResponse;
import cl.duoc.cdy2204.formativa.dto.ResumenInscripcionMqProducerResponse;
import cl.duoc.cdy2204.formativa.dto.ResumenInscripcionMqResponse;
import cl.duoc.cdy2204.formativa.service.InscripcionService;
import cl.duoc.cdy2204.formativa.service.ResumenInscripcionService;
import cl.duoc.cdy2204.formativa.service.ResumenInscripcionMqService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/inscripciones")
public class InscripcionController {

    private final InscripcionService inscripcionService;
    private final ResumenInscripcionService resumenInscripcionService;
    private final ResumenInscripcionMqService resumenInscripcionMqService;

    public InscripcionController(
            InscripcionService inscripcionService,
            ResumenInscripcionService resumenInscripcionService,
            ResumenInscripcionMqService resumenInscripcionMqService
    ) {
        this.inscripcionService = inscripcionService;
        this.resumenInscripcionService = resumenInscripcionService;
        this.resumenInscripcionMqService = resumenInscripcionMqService;
    }

    @PostMapping
    public ResponseEntity<InscripcionResponse> inscribir(@Valid @RequestBody InscripcionRequest request) {
        InscripcionResponse response = inscripcionService.inscribir(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{numeroResumen}/resumen")
    public ResponseEntity<byte[]> descargarResumen(@PathVariable @Positive Long numeroResumen) {
        byte[] archivo = resumenInscripcionService.leerArchivoResumen(numeroResumen);
        ContentDisposition disposition = ContentDisposition.attachment()
                .filename("resumen-" + numeroResumen + ".txt")
                .build();

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.CONTENT_DISPOSITION, disposition.toString())
                .body(archivo);
    }

    @PostMapping("/{numeroResumen}/resumenes-mq/producir")
    public ResponseEntity<ResumenInscripcionMqProducerResponse> producirResumenMq(
            @PathVariable @Positive Long numeroResumen
    ) {
        return ResponseEntity.ok(resumenInscripcionService.publicarResumenEnCola(numeroResumen));
    }

    @PostMapping("/resumenes-mq/consumir")
    public ResponseEntity<ResumenInscripcionMqResponse> consumirResumenMq() {
        return resumenInscripcionMqService.consumirYGuardarResumen()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}
