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

    // Enviar comentario a la cola
    @Autowired
    private ObjectMapper objectMapper; // Jackson para manejar JSON

    public void sendCommentToQueue(ComentarioDTO comentario) {
        try {
            System.out.println(comentario.getDescripcion() + " " + comentario.getRating());
            String comentarioJson = objectMapper.writeValueAsString(comentario);
            System.out.println(comentarioJson);
            rabbitTemplate.convertAndSend(RabbitMQConfig.COMMENT_QUEUE, comentarioJson);
        } catch (Exception e) {
            System.err.println("Error al convertir comentario a JSON: " + e.getMessage());
        }
    }

    // Aceptar comentario
    public void aceptarComentario(ComentarioDTO comentarioDTO) {
        // Busca el usuario y el lugar usando los IDs proporcionados en comentarioDTO
        Usuario usuario = usuarioRepository.findByEmail(comentarioDTO.getUsuario());
        Lugar lugar = lugarRepository.findById(comentarioDTO.getLugarId())
                .orElseThrow(() -> new IllegalArgumentException("Lugar no encontrado"));

        // Crea el comentario con los datos proporcionados
        Comentario comentario = new Comentario(usuario, lugar, comentarioDTO.getDescripcion(), comentarioDTO.getRating());

        // Agrega el comentario y actualiza el rating promedio
        comentarioMgr.addComentario(comentario);

        // Elimina el comentario de la cola
        //eliminarDeLaCola(comentarioDTO);
    }

    // Rechazar comentario
    public void rechazarComentario(ComentarioDTO comentarioDTO) {
        // Simplemente eliminamos el comentario de la cola sin guardarlo en la base de datos
        //eliminarDeLaCola(comentarioDTO);
    }

    // Método para eliminar el comentario de la cola
    private void eliminarDeLaCola(Long comentarioId) {
        // Lógica para eliminar el comentario de la cola
        // Puedes implementar esta lógica según cómo manejes la cola en tu configuración de RabbitMQ
        // Esto puede variar según la implementación de la librería o framework de RabbitMQ
        System.out.println("Comentario eliminado de la cola con ID: " + comentarioId);
    }
}