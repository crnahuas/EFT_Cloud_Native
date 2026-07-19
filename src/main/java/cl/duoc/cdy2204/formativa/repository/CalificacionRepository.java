package cl.duoc.cdy2204.formativa.repository;

import cl.duoc.cdy2204.formativa.entity.Calificacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {

    List<Calificacion> findByInscripcionIdOrderByFechaRegistroDesc(Long inscripcionId);
}
