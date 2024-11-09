package TipsUY.backend.controllers;

import TipsUY.backend.entities.DTOs.ComentarioDTO;
import TipsUY.backend.services.ComentarioQueueConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/comentarios")
public class AdminComentarioController {

    @Autowired
    private ComentarioQueueConsumer comentarioQueueConsumer;

    @GetMapping("/pendientes")
    public List<ComentarioDTO> obtenerComentariosPendientes() {
        System.out.println("Comentarios pendientes: " + comentarioQueueConsumer.getPendingComments());
        return comentarioQueueConsumer.getPendingComments().stream().collect(Collectors.toList());
    }
}
