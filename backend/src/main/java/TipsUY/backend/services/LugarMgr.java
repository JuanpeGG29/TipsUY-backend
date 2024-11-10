package TipsUY.backend.services;

import TipsUY.backend.entities.DTOs.ComentarioDTO;
import TipsUY.backend.entities.DTOs.LugarDTO;
import TipsUY.backend.entities.Foto;
import TipsUY.backend.entities.Lugar;
import TipsUY.backend.exceptions.EntidadYaExiste;
import TipsUY.backend.exceptions.InformacionInvalida;
import TipsUY.backend.persistence.LugarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/search")
    public List<LugarDTO> searchPlaces(String name, String city, String type) {
        List<Lugar> lugares = lugarRepository.findAll();

        // Aplicar los filtros en la lista de lugares
        if (name != null && !name.isEmpty()) {
            lugares = lugares.stream()
                    .filter(lugar -> lugar.getNombre().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (city != null && !city.isEmpty()) {
            lugares = lugares.stream()
                    .filter(lugar -> lugar.getUbicacion().getDireccion() != null && lugar.getUbicacion().getDireccion().toLowerCase().contains(city.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (type != null && !type.isEmpty()) {
            lugares = lugares.stream()
                    .filter(lugar -> lugar.getTipo() != null && lugar.getTipo().toLowerCase().contains(type.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Convertir los lugares filtrados a LugarDTO sin usar un constructor adicional
        List<LugarDTO> lugarDTOs = lugares.stream().map(lugar -> new LugarDTO(
                lugar.getId(),
                lugar.getNombre(),
                lugar.getDescripcion(),
                lugar.getTipo(),
                lugar.getHorarios(),
                lugar.getPrecios(),
                lugar.getContacto(),
                lugar.getRatingPromedio(),
                lugar.getFotos().stream()
                        .map(Foto::getUrl)
                        .collect(Collectors.toList()),
                lugar.getUbicacion() != null ? lugar.getUbicacion().getDireccion() : null,
                lugar.getComentarios().stream()
                        .map(comentario -> new ComentarioDTO(
                                comentario.getUsuario().getEmail(),
                                comentario.getDescripcion(),
                                comentario.getFecha(),
                                comentario.getRating(),
                                comentario.getId()
                        ))
                        .collect(Collectors.toList())
        )).collect(Collectors.toList());

        return lugarDTOs;
    }
}
