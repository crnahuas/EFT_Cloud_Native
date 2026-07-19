package cl.duoc.cdy2204.formativa.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import cl.duoc.cdy2204.formativa.dto.ExamenRequest;
import cl.duoc.cdy2204.formativa.dto.ExamenResponse;
import cl.duoc.cdy2204.formativa.entity.Curso;
import cl.duoc.cdy2204.formativa.entity.Examen;
import cl.duoc.cdy2204.formativa.repository.CursoRepository;
import cl.duoc.cdy2204.formativa.repository.ExamenRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExamenServiceTest {

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private ExamenRepository examenRepository;

    @Test
    void crearExamenLoAsociaAlCurso() {
        Curso curso = curso();
        ExamenRequest request = request();
        when(cursoRepository.findById(10L)).thenReturn(Optional.of(curso));
        when(examenRepository.save(org.mockito.ArgumentMatchers.any(Examen.class)))
                .thenAnswer(invocation -> {
                    Examen examen = invocation.getArgument(0);
                    examen.setId(30L);
                    return examen;
                });

        ExamenResponse response = service().crear(10L, request);

        assertThat(response.getId()).isEqualTo(30L);
        assertThat(response.getCursoId()).isEqualTo(10L);
        assertThat(response.getPuntajeMaximo()).isEqualByComparingTo("100");
    }

    private ExamenService service() {
        return new ExamenService(cursoRepository, examenRepository);
    }

    private Curso curso() {
        Curso curso = new Curso();
        curso.setId(10L);
        curso.setNombre("Cloud Native");
        return curso;
    }

    private ExamenRequest request() {
        ExamenRequest request = new ExamenRequest();
        request.setTitulo("Evaluacion Cloud Native");
        request.setDescripcion("Examen del curso");
        request.setFechaDisponible(LocalDateTime.of(2026, 7, 18, 20, 0));
        request.setPuntajeMaximo(new BigDecimal("100"));
        return request;
    }
}
