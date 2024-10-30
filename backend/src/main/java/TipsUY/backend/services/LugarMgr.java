package TipsUY.backend.services;

import TipsUY.backend.entities.Lugar;
import TipsUY.backend.exceptions.EntidadYaExiste;
import TipsUY.backend.exceptions.InformacionInvalida;
import TipsUY.backend.persistence.LugarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LugarMgr {
    @Autowired
    private LugarRepository lugarRepository;

    public void addLugar(Lugar lugar) throws InformacionInvalida, EntidadYaExiste {

        if ("".equals(lugar.getNombre())) {

            throw new InformacionInvalida("Alguno de los datos ingresados no es correcto");
        }

        if (lugarRepository.findOneByNombre(lugar.getNombre()) != null) {
            throw new EntidadYaExiste();
        }

        lugarRepository.save(lugar);
    }
}
