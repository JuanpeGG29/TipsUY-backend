package TipsUY.backend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "lugar_id")
    @JsonBackReference
    private Lugar lugar;

    @Column(nullable = false)
    private String url;  // URL de la imagen

    private LocalDate fechaSubida;

    // Constructor personalizado para inicialización rápida
    public Foto(Usuario usuario, Lugar lugar, String url, LocalDate fechaSubida) {
        this.usuario = usuario;
        this.lugar = lugar;
        this.url = url;
        this.fechaSubida = fechaSubida;
    }
}