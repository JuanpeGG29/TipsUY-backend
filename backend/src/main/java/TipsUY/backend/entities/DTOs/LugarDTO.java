package TipsUY.backend.entities.DTOs;

import java.util.List;

public class LugarDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String tipo;
    private String contacto;
    private String precios;
    private String horarios;
    private Double ratingPromedio;
    private List<String> fotos;
    private String direccion;
    private List<ComentarioDTO> comentarios; // Nueva lista de comentarios

    public LugarDTO(Long id, String nombre, String descripcion, String tipo, String horarios, String precios, String contacto, Double ratingPromedio,
                    List<String> fotos, String direccion, List<ComentarioDTO> comentarios) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.horarios = horarios;
        this.precios = precios;
        this.contacto = contacto;
        this.ratingPromedio = ratingPromedio;
        this.fotos = fotos;
        this.direccion = direccion;
        this.comentarios = comentarios;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getPrecios() {
        return precios;
    }

    public void setPrecios(String precios) {
        this.precios = precios;
    }

    public String getHorarios() {
        return horarios;
    }

    public void setHorarios(String horarios) {
        this.horarios = horarios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getRatingPromedio() {
        return ratingPromedio;
    }

    public void setRatingPromedio(Double ratingPromedio) {
        this.ratingPromedio = ratingPromedio;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<ComentarioDTO> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<ComentarioDTO> comentarios) {
        this.comentarios = comentarios;
    }
}