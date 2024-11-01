package TipsUY.backend.controllers;

import TipsUY.backend.entities.DTOs.LugarDTO;
import TipsUY.backend.entities.Lugar;
import TipsUY.backend.persistence.LugarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lugares")
@CrossOrigin(origins = "http://localhost:3000")
public class LugarController {

    @Autowired
    private LugarRepository lugarRepository;

    @GetMapping("/top-rated")
    public List<LugarDTO> obtenerTopLugares() {
        List<Lugar> lugares = lugarRepository.findTop3ByOrderByRatingPromedioDesc();

        // Convertir cada Lugar a LugarDTO
        return lugares.stream().map(lugar -> new LugarDTO(
                lugar.getId(),
                lugar.getNombre(),
                lugar.getDescripcion(),
                lugar.getTipo(),
                lugar.getRatingPromedio(),
                lugar.getFotos().stream()
                        .map(foto -> "/images/" + foto.getUrl()) // Ajusta esta línea para obtener la URL completa de la imagen
                        .collect(Collectors.toList())
        )).collect(Collectors.toList());
    }
}