package TipsUY.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String cedula;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String tipo; // 'administrador' o 'visitante'

    private LocalDate fechaRegistro;

    @OneToMany(mappedBy = "usuario")
    private List<Comentario> comentarios;

    @OneToMany(mappedBy = "usuario")
    private List<Foto> fotos;

    public Usuario(String nombre, String email, String password, String cedula, String tipo, LocalDate fechaRegistro) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.cedula = cedula;
        this.tipo = tipo;
        this.fechaRegistro = fechaRegistro;
    }
}