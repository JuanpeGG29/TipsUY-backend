package TipsUY.backend.services;

import TipsUY.backend.entities.Comentario;
import TipsUY.backend.entities.Lugar;
import TipsUY.backend.persistence.ComentarioRepository;
import TipsUY.backend.persistence.LugarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComentarioMgr {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private LugarRepository lugarRepository;

    public void addComentario(Comentario comentario) {
        Lugar lugar = comentario.getLugar();

        // Asegurarse de que la lista de comentarios no sea null
        List<Comentario> comentarios = lugar.getComentarios();
        if (comentarios == null) {
            comentarios = new ArrayList<>();
            lugar.setComentarios(comentarios);
        }

        // Añadir el nuevo comentario a la lista de comentarios
        comentarios.add(comentario);

        // Calcular el nuevo promedio de rating
        double sumaRatings = comentarios.stream().mapToDouble(Comentario::getRating).sum();
        double nuevoRatingPromedio = sumaRatings / comentarios.size();

        // Actualizar el rating promedio del lugar
        lugar.setRatingPromedio(nuevoRatingPromedio);
        lugarRepository.save(lugar);  // Guardamos el lugar con el nuevo rating promedio

        // Finalmente, guardamos el comentario
        comentarioRepository.save(comentario);
    }
}
