package cl.duoc.cdy2204.formativa.repository;

import cl.duoc.cdy2204.formativa.entity.ContenidoCurso;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContenidoCursoRepository extends JpaRepository<ContenidoCurso, Long> {

    List<ContenidoCurso> findByCursoIdOrderByIdAsc(Long cursoId);
}
