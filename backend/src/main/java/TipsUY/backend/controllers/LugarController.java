package TipsUY.backend.controllers;

import TipsUY.backend.entities.DTOs.ComentarioDTO;
import TipsUY.backend.entities.DTOs.LugarDTO;
import TipsUY.backend.entities.Foto;
import TipsUY.backend.entities.Lugar;
import TipsUY.backend.persistence.LugarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lugares")
@CrossOrigin(origins = "http://localhost:3000")
public class LugarController {

    @Autowired
    private LugarRepository lugarRepository;

    // Endpoint para obtener los lugares top-rated, sin incluir comentarios
    @GetMapping("/top-rated")
    public List<LugarDTO> obtenerTopLugares() {
        List<Lugar> lugares = lugarRepository.findTop3ByOrderByRatingPromedioDesc();

        // Convertir cada Lugar a LugarDTO sin comentarios
        return lugares.stream().map(lugar -> new LugarDTO(
                lugar.getId(),
                lugar.getNombre(),
                lugar.getDescripcion(),
                lugar.getTipo(),
                lugar.getRatingPromedio(),
                lugar.getFotos().stream()
                        .map(Foto::getUrl) // Utilizar la URL completa de la imagen
                        .collect(Collectors.toList()),
                lugar.getUbicacion() != null ? lugar.getUbicacion().getDireccion() : null, // Asigna la dirección si está disponible
                null // No incluimos comentarios en el top-rated
        )).collect(Collectors.toList());
    }

    // Endpoint para obtener detalles de un lugar específico, incluyendo comentarios
    @GetMapping("/{id}")
    public ResponseEntity<LugarDTO> obtenerLugarPorId(@PathVariable Long id) {
        Lugar lugar = lugarRepository.findById(id).orElse(null);

        if (lugar == null) {
            return ResponseEntity.notFound().build();
        }

        LugarDTO lugarDTO = new LugarDTO(
                lugar.getId(),
                lugar.getNombre(),
                lugar.getDescripcion(),
                lugar.getTipo(),
                lugar.getRatingPromedio(),
                lugar.getFotos().stream()
                        .map(Foto::getUrl)
                        .collect(Collectors.toList()),
                lugar.getUbicacion() != null ? lugar.getUbicacion().getDireccion() : null,
                lugar.getComentarios().stream()
                        .map(comentario -> new ComentarioDTO(
                                comentario.getUsuario().getNombre(),
                                comentario.getDescripcion(),
                                comentario.getFecha(),
                                comentario.getRating()
                        ))
                        .collect(Collectors.toList()) // Incluir comentarios en este endpoint
        );

        return ResponseEntity.ok(lugarDTO);
    }
}


