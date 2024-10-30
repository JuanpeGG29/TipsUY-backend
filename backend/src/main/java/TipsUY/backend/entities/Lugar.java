package TipsUY.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Lugar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false)
    private String tipo; // 'Agreste', 'Ciudad', 'Camping', 'Restaurante', etc.

    @OneToOne(mappedBy = "lugar", cascade = CascadeType.ALL)
    private Ubicacion ubicacion;

    private String horarios;

    private String precios;

    private String contacto;  // Teléfono o email

    @OneToMany(mappedBy = "lugar", fetch = FetchType.LAZY)
    private List<Foto> fotos;

    @OneToMany(mappedBy = "lugar", fetch = FetchType.LAZY)
    private List<Comentario> comentarios;

    private Double ratingPromedio;

    // Constructor personalizado para inicialización rápida
    public Lugar(String nombre, String descripcion, String tipo, String horarios, String precios, String contacto, Double ratingPromedio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.horarios = horarios;
        this.precios = precios;
        this.contacto = contacto;
        this.ratingPromedio = ratingPromedio;
    }
}