package TipsUY.backend.controllers.Usuario;

import TipsUY.backend.entities.Usuario;
import TipsUY.backend.exceptions.EntidadYaExiste;
import TipsUY.backend.exceptions.InformacionInvalida;
import TipsUY.backend.services.UsuarioMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class RegistroController {

    @Autowired
    private UsuarioMgr usuarioMgr;

    @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody Usuario usuario) {
        try {
            usuario.setFechaRegistro(LocalDate.now());
            usuario.setTipo("turista");
            usuarioMgr.addUsuario(usuario);
            return ResponseEntity.ok("Usuario registrado exitosamente");
        } catch (EntidadYaExiste e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un usuario con ese email");
        } catch (InformacionInvalida e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cedula y/o email incorrectos");
        }
    }
}
