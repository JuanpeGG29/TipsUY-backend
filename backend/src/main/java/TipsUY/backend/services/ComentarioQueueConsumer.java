package TipsUY.backend.services;

import TipsUY.backend.RabbitMQConfig;
import TipsUY.backend.entities.DTOs.ComentarioDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class ComentarioQueueConsumer {

    private final ConcurrentLinkedQueue<ComentarioDTO> pendingComments = new ConcurrentLinkedQueue<>();
    private final ObjectMapper objectMapper = new ObjectMapper(); // Instancia de Jackson para manejar JSON

    @RabbitListener(queues = RabbitMQConfig.COMMENT_QUEUE)
    public void receiveComment(String comentarioJson) {
        try {
            ComentarioDTO comentario = objectMapper.readValue(comentarioJson, ComentarioDTO.class);
            pendingComments.add(comentario);
            System.out.println("Comentario agregado a pendientes: " + comentario);
        } catch (Exception e) {
            System.err.println("Error al convertir JSON a ComentarioDTO: " + e.getMessage());
        }
    }

    public ConcurrentLinkedQueue<ComentarioDTO> getPendingComments() {
        return pendingComments;
    }
}