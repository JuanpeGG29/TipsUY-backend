package TipsUY.backend.persistence;

import TipsUY.backend.entities.Lugar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LugarRepository extends JpaRepository<Lugar, Long> {
    Lugar findOneByNombre(String nombre);
    List<Lugar> findTop3ByOrderByRatingPromedioDesc();
}