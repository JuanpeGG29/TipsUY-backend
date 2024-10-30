package TipsUY.backend.persistence;

import TipsUY.backend.entities.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UbicacionRepository extends JpaRepository<Ubicacion, Long> {
    Ubicacion findOneByDireccion(String direccion);
}
