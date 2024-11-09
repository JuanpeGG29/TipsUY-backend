package TipsUY.backend.controllers;

import TipsUY.backend.entities.DTOs.ComentarioDTO;
import TipsUY.backend.services.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @PostMapping("/enviar")
    public ResponseEntity<String> enviarComentario(@RequestBody ComentarioDTO comentarioDTO) {
        comentarioService.sendCommentToQueue(comentarioDTO);
        return ResponseEntity.ok("Comentario enviado a la cola de revisión.");
    }

    @PostMapping("/aceptar")
    public ResponseEntity<String> aceptarComentario(@RequestBody ComentarioDTO comentarioDTO) {
        comentarioService.aceptarComentario(comentarioDTO);
        return ResponseEntity.ok("Comentario aceptado.");
    }

    @PostMapping("/rechazar")
    public ResponseEntity<String> rechazarComentario(@RequestBody ComentarioDTO comentarioDTO) {
        comentarioService.rechazarComentario(comentarioDTO);
        return ResponseEntity.ok("Comentario rechazado.");
    }
}
