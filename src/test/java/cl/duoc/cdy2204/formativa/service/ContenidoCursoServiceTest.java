package cl.duoc.cdy2204.formativa.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import cl.duoc.cdy2204.formativa.dto.ContenidoCursoRequest;
import cl.duoc.cdy2204.formativa.dto.ContenidoCursoResponse;
import cl.duoc.cdy2204.formativa.entity.ContenidoCurso;
import cl.duoc.cdy2204.formativa.entity.Curso;
import cl.duoc.cdy2204.formativa.repository.ContenidoCursoRepository;
import cl.duoc.cdy2204.formativa.repository.CursoRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ContenidoCursoServiceTest {

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private ContenidoCursoRepository contenidoCursoRepository;

    @Test
    void crearContenidoLoAsociaAlCurso() {
        Curso curso = curso();
        ContenidoCursoRequest request = request();
        when(cursoRepository.findById(10L)).thenReturn(Optional.of(curso));
        when(contenidoCursoRepository.save(org.mockito.ArgumentMatchers.any(ContenidoCurso.class)))
                .thenAnswer(invocation -> {
                    ContenidoCurso contenido = invocation.getArgument(0);
                    contenido.setId(20L);
                    return contenido;
                });

        ContenidoCursoResponse response = service().crear(10L, request);

        assertThat(response.getId()).isEqualTo(20L);
        assertThat(response.getCursoId()).isEqualTo(10L);
        assertThat(response.getTitulo()).isEqualTo("Material introductorio");
    }

    private ContenidoCursoService service() {
        return new ContenidoCursoService(cursoRepository, contenidoCursoRepository);
    }

    private Curso curso() {
        Curso curso = new Curso();
        curso.setId(10L);
        curso.setNombre("Cloud Native");
        return curso;
    }

    private ContenidoCursoRequest request() {
        ContenidoCursoRequest request = new ContenidoCursoRequest();
        request.setTitulo("Material introductorio");
        request.setDescripcion("Contenido base del curso");
        request.setUrlContenido("https://example.com/contenido");
        return request;
    }
}
