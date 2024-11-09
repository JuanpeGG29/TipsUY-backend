package TipsUY.backend.entities.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class ComentarioDTO {
    private String usuario;
    private String descripcion;
    @JsonIgnore
    private LocalDateTime fecha;
    private Integer rating;
    private Long lugarId; // Agregar este campo

    public ComentarioDTO(String usuario, String descripcion, LocalDateTime fecha, Integer rating, Long lugarId) {
        this.usuario = usuario;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.rating = rating;
        this.lugarId = lugarId;
    }

    @Override
    public String toString() {
        return "ComentarioDTO{" +
                "usuario='" + usuario + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", rating=" + rating +
                ", lugarId=" + lugarId +
                '}';
    }

    public Long getLugarId() {
        return lugarId;
    }

    public void setLugarId(Long lugarId) {
        this.lugarId = lugarId;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
