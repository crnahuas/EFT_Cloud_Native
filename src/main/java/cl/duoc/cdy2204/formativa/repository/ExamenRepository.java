package cl.duoc.cdy2204.formativa.repository;

import cl.duoc.cdy2204.formativa.entity.Examen;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamenRepository extends JpaRepository<Examen, Long> {

    List<Examen> findByCursoIdOrderByFechaDisponibleAsc(Long cursoId);
}
