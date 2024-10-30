package TipsUY.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "lugar_id", nullable = false)
    private Lugar lugar;

    @Column(nullable = false)
    private double latitud;

    @Column(nullable = false)
    private double longitud;

    private String direccion;  // Dirección exacta o aproximada

    // Constructor personalizado para inicialización rápida
    public Ubicacion(Lugar lugar, double latitud, double longitud, String direccion) {
        this.lugar = lugar;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion = direccion;
    }
}