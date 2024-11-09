package TipsUY.backend.services;

import TipsUY.backend.RabbitMQConfig;
import TipsUY.backend.entities.Comentario;
import TipsUY.backend.entities.DTOs.ComentarioDTO;
import TipsUY.backend.entities.Lugar;
import TipsUY.backend.entities.Usuario;
import TipsUY.backend.persistence.ComentarioRepository;
import TipsUY.backend.persistence.LugarRepository;
import TipsUY.backend.persistence.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComentarioService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ComentarioMgr comentarioMgr;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private LugarRepository lugarRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ComentarioQueueConsumer comentarioQueueConsumer; // Inyectamos el consumidor de la cola

    @Autowired
    private ObjectMapper objectMapper;

    public void sendCommentToQueue(ComentarioDTO comentario) {
        try {
            String comentarioJson = objectMapper.writeValueAsString(comentario);
            rabbitTemplate.convertAndSend(RabbitMQConfig.COMMENT_QUEUE, comentarioJson);
        } catch (Exception e) {
            System.err.println("Error al convertir comentario a JSON: " + e.getMessage());
        }
    }

    public void aceptarComentario(ComentarioDTO comentarioDTO) {
        Usuario usuario = usuarioRepository.findByEmail(comentarioDTO.getUsuario());
        Lugar lugar = lugarRepository.findById(comentarioDTO.getLugarId())
                .orElseThrow(() -> new IllegalArgumentException("Lugar no encontrado"));

        Comentario comentario = new Comentario(usuario, lugar, comentarioDTO.getDescripcion(), comentarioDTO.getRating());
        comentarioMgr.addComentario(comentario);

        eliminarDeLaCola(comentarioDTO);
    }

    public void rechazarComentario(ComentarioDTO comentarioDTO) {
        eliminarDeLaCola(comentarioDTO);
    }

    private void eliminarDeLaCola(ComentarioDTO comentarioDTO) {
        comentarioQueueConsumer.getPendingComments().removeIf(
                c -> c.getUsuario().equals(comentarioDTO.getUsuario()) &&
                        c.getDescripcion().equals(comentarioDTO.getDescripcion()) &&
                        c.getRating().equals(comentarioDTO.getRating()) &&
                        c.getLugarId().equals(comentarioDTO.getLugarId())
        );
        System.out.println("Comentario eliminado de la cola: " + comentarioDTO);
    }
}