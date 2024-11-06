package TipsUY.backend.entities.DTOs;

import java.time.LocalDateTime;

public class ComentarioDTO {
    private String usuario; // Nombre o identificador del usuario
    private String descripcion;
    private LocalDateTime fecha;
    private Integer rating;

    public ComentarioDTO(String usuario, String descripcion, LocalDateTime fecha, Integer rating) {
        this.usuario = usuario;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.rating = rating;
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
