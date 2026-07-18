package cl.duoc.cdy2204.formativa.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cl.duoc.cdy2204.formativa.entity.Curso;
import cl.duoc.cdy2204.formativa.entity.Inscripcion;
import cl.duoc.cdy2204.formativa.repository.InscripcionRepository;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ResumenInscripcionServiceTest {

    @Mock
    private InscripcionRepository inscripcionRepository;

    @Mock
    private ResumenInscripcionMqService resumenInscripcionMqService;

    @TempDir
    private Path tempDir;

    @Test
    void leerArchivoResumenGeneraArchivoSinPublicarEnRabbitMq() throws Exception {
        Inscripcion inscripcion = inscripcion();
        when(inscripcionRepository.findById(10L)).thenReturn(Optional.of(inscripcion));
        ResumenInscripcionService service = service();

        byte[] archivo = service.leerArchivoResumen(10L);

        String contenido = new String(archivo, StandardCharsets.UTF_8);
        assertThat(contenido).contains("Resumen de inscripcion");
        assertThat(contenido).contains("Numero resumen: 10");
        assertThat(Files.exists(tempDir.resolve("10").resolve("resumen.txt"))).isTrue();
        verify(resumenInscripcionMqService, never()).enviarResumen(
                org.mockito.ArgumentMatchers.any(),
                org.mockito.ArgumentMatchers.anyString()
        );
    }

    @Test
    void publicarResumenEnColaPublicaEnRabbitMq() {
        Inscripcion inscripcion = inscripcion();
        when(inscripcionRepository.findById(10L)).thenReturn(Optional.of(inscripcion));
        ResumenInscripcionService service = service();

        service.publicarResumenEnCola(10L);

        verify(resumenInscripcionMqService).enviarResumen(
                org.mockito.ArgumentMatchers.eq(inscripcion),
                org.mockito.ArgumentMatchers.contains("Resumen de inscripcion")
        );
    }

    private ResumenInscripcionService service() {
        return new ResumenInscripcionService(
                inscripcionRepository,
                resumenInscripcionMqService,
                tempDir.toString()
        );
    }

    private Inscripcion inscripcion() {
        Curso curso = new Curso();
        curso.setId(1L);
        curso.setNombre("Spring Boot Cloud Native");
        curso.setInstructor("Docente CDY2204");
        curso.setDuracion("24 horas");
        curso.setCosto(new BigDecimal("120000"));

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setId(10L);
        inscripcion.setEstudianteNombre("Maria Perez");
        inscripcion.setEstudianteEmail("maria.perez@duocuc.cl");
        inscripcion.setFechaInscripcion(LocalDateTime.of(2026, 7, 6, 12, 0));
        inscripcion.setTotal(new BigDecimal("120000"));
        inscripcion.setCursos(List.of(curso));
        return inscripcion;
    }
}
