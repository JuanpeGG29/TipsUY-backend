package TipsUY.backend.services;

import TipsUY.backend.entities.Ubicacion;
import TipsUY.backend.exceptions.EntidadYaExiste;
import TipsUY.backend.exceptions.InformacionInvalida;
import TipsUY.backend.persistence.UbicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UbicacionMgr {
    @Autowired
    private UbicacionRepository ubicacionRepository;
    public void addUbicacion(Ubicacion ubicacion) throws InformacionInvalida, EntidadYaExiste {

        if (ubicacion.getLugar() == null || ubicacion.getLatitud() == 0.0 || ubicacion.getLongitud() == 0.0) {

            throw new InformacionInvalida("Alguno de los datos ingresados no es correcto");
        }

        ubicacionRepository.save(ubicacion);
    }
}
