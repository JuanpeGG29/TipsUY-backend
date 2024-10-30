package TipsUY.backend.services;

import TipsUY.backend.entities.Foto;
import TipsUY.backend.exceptions.EntidadYaExiste;
import TipsUY.backend.exceptions.InformacionInvalida;
import TipsUY.backend.persistence.FotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FotoMgr {

    @Autowired
    private FotoRepository fotoRepository;
    public void addFoto(Foto foto) throws InformacionInvalida, EntidadYaExiste {

        if ("".equals(foto.getUrl())) {

            throw new InformacionInvalida("Alguno de los datos ingresados no es correcto");
        }

        if (fotoRepository.findOneByUrl(foto.getUrl()) != null) {
            throw new EntidadYaExiste();
        }

        fotoRepository.save(foto);
    }
}
