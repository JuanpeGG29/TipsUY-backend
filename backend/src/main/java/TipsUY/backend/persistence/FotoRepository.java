package TipsUY.backend.persistence;

import TipsUY.backend.entities.Foto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FotoRepository extends JpaRepository<Foto, Long> {
    Foto findOneByUrl(String Url);
}
