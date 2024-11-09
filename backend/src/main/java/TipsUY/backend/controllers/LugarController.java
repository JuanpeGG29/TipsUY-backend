package TipsUY.backend.controllers;

import TipsUY.backend.entities.DTOs.ComentarioDTO;
import TipsUY.backend.entities.DTOs.LugarDTO;
import TipsUY.backend.entities.Foto;
import TipsUY.backend.entities.Lugar;
import TipsUY.backend.entities.Ubicacion;
import TipsUY.backend.persistence.LugarRepository;
import TipsUY.backend.services.IBMCloudStorageService;
import TipsUY.backend.services.LugarMgr;
import TipsUY.backend.services.UbicacionMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lugares")
@CrossOrigin(origins = "http://localhost:3000")
public class LugarController {

    @Autowired
    private LugarRepository lugarRepository;

    @Autowired
    private LugarMgr lugarMgr;
    @Autowired
    private UbicacionMgr ubicacionMgr;

    private final IBMCloudStorageService ibmCloudStorageService;

    @Autowired
    public LugarController(IBMCloudStorageService ibmCloudStorageService) {
        this.ibmCloudStorageService = ibmCloudStorageService;
    }

    @PostMapping("add-lugar")
    public ResponseEntity<?> agregarLugar(@RequestParam("nombre") String nombre,
                                          @RequestParam("descripcion") String descripcion,
                                          @RequestParam("tipo") String tipo,
                                          @RequestParam("latitude") double latitude,
                                          @RequestParam("longitude") double longitude,
                                          @RequestParam("horarios") String horarios,
                                          @RequestParam("precios") String precios,
                                          @RequestParam("contacto") String contacto,
                                          @RequestParam("fotos") MultipartFile[] fotos) {
        try {
            System.out.println("PUDO ENTRAR AL CONTROLLER ADD LUGAR");
            System.out.println(nombre + descripcion + tipo + horarios);

            // Crear y guardar el lugar sin fotos ni ubicación inicialmente
            Lugar lugar = new Lugar(nombre, descripcion, tipo, horarios, precios, contacto, 4.0);
            lugarMgr.addLugar(lugar);  // Guardar lugar en la base de datos
            System.out.println("Creo el lugar y lo guardo en la BD");

            // Subir y asociar cada foto en IBM Cloud Object Storage y al lugar
            for (MultipartFile foto : fotos) {
                String fileName = nombre + ":" + foto.getOriginalFilename();
                String url = ibmCloudStorageService.uploadFile("bucket-demos", fileName, foto.getBytes());
                System.out.println("SUBIO LA FOTO");
                lugar.getFotos().add(new Foto(null, lugar, url, LocalDate.now()));
                System.out.println("PASO AGREGAR FOTO");
            }

            // Crear y asociar la ubicación ahora que el lugar está guardado
            Ubicacion ubicacion = new Ubicacion(lugar, latitude, longitude, null);
            ubicacionMgr.addUbicacion(ubicacion);

            System.out.println("LLEGO HASTA EL RETURN");
            return ResponseEntity.ok("Lugar agregado exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();  // Esto mostrará más detalles del error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al agregar lugar.");
        }
    }

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
                                comentario.getUsuario().getEmail(),
                                comentario.getDescripcion(),
                                comentario.getFecha(),
                                comentario.getRating(),
                                comentario.getId()
                        ))
                        .collect(Collectors.toList()) // Incluir comentarios en este endpoint
        );

        return ResponseEntity.ok(lugarDTO);
    }
}


