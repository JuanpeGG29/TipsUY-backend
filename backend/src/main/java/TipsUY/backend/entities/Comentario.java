package TipsUY.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "lugar_id")
    private Lugar lugar;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    private LocalDateTime fecha = LocalDateTime.now();

    @Column(nullable = false)
    private Integer rating;

    // Constructor personalizado para inicialización rápida
    public Comentario(Usuario usuario, Lugar lugar, String descripcion, Integer rating) {
        this.usuario = usuario;
        this.lugar = lugar;
        this.descripcion = descripcion;
        this.rating = rating;
    }
}