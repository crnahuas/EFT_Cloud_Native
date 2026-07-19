package cl.duoc.cdy2204.formativa.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import cl.duoc.cdy2204.formativa.dto.CalificacionRequest;
import cl.duoc.cdy2204.formativa.dto.CalificacionResponse;
import cl.duoc.cdy2204.formativa.entity.Calificacion;
import cl.duoc.cdy2204.formativa.entity.Curso;
import cl.duoc.cdy2204.formativa.entity.Examen;
import cl.duoc.cdy2204.formativa.entity.Inscripcion;
import cl.duoc.cdy2204.formativa.repository.CalificacionRepository;
import cl.duoc.cdy2204.formativa.repository.ExamenRepository;
import cl.duoc.cdy2204.formativa.repository.InscripcionRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CalificacionServiceTest {

    @Mock
    private CalificacionRepository calificacionRepository;

    @Mock
    private InscripcionRepository inscripcionRepository;

    @Mock
    private ExamenRepository examenRepository;

    @Test
    void registrarCalificacionRelacionaInscripcionYExamen() {
        Inscripcion inscripcion = inscripcion();
        Examen examen = examen();
        when(inscripcionRepository.findById(11L)).thenReturn(Optional.of(inscripcion));
        when(examenRepository.findById(30L)).thenReturn(Optional.of(examen));
        when(calificacionRepository.save(org.mockito.ArgumentMatchers.any(Calificacion.class)))
                .thenAnswer(invocation -> {
                    Calificacion calificacion = invocation.getArgument(0);
                    calificacion.setId(40L);
                    return calificacion;
                });

        CalificacionResponse response = service().registrar(request());

        assertThat(response.getId()).isEqualTo(40L);
        assertThat(response.getInscripcionId()).isEqualTo(11L);
        assertThat(response.getExamenId()).isEqualTo(30L);
        assertThat(response.getPuntaje()).isEqualByComparingTo("92");
    }

    @Test
    void registrarCalificacionRechazaExamenDeCursoNoInscrito() {
        Inscripcion inscripcion = inscripcion();
        inscripcion.getCursos().clear();
        when(inscripcionRepository.findById(11L)).thenReturn(Optional.of(inscripcion));
        when(examenRepository.findById(30L)).thenReturn(Optional.of(examen()));

        assertThatThrownBy(() -> service().registrar(request()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La inscripcion no pertenece al curso");
    }

    private CalificacionService service() {
        return new CalificacionService(calificacionRepository, inscripcionRepository, examenRepository);
    }

    private CalificacionRequest request() {
        CalificacionRequest request = new CalificacionRequest();
        request.setInscripcionId(11L);
        request.setExamenId(30L);
        request.setPuntaje(new BigDecimal("92"));
        request.setComentario("Buen desempeno");
        return request;
    }

    private Inscripcion inscripcion() {
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setId(11L);
        inscripcion.setEstudianteNombre("Maria Perez");
        Curso curso = new Curso();
        curso.setId(10L);
        inscripcion.getCursos().add(curso);
        return inscripcion;
    }

    private Examen examen() {
        Curso curso = new Curso();
        curso.setId(10L);

        Examen examen = new Examen();
        examen.setId(30L);
        examen.setCurso(curso);
        examen.setTitulo("Evaluacion Cloud Native");
        examen.setPuntajeMaximo(new BigDecimal("100"));
        examen.setFechaDisponible(LocalDateTime.of(2026, 7, 18, 20, 0));
        return examen;
    }
}
