package TipsUY.backend.controllers.Usuario;

import TipsUY.backend.entities.Usuario;
import TipsUY.backend.exceptions.EntidadNoExiste;
import TipsUY.backend.services.UsuarioMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class LoginController {

    @Autowired
    private UsuarioMgr usuarioMgr;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        try {
            System.out.println("Solicitud de inicio de sesión recibida: " + username + password);
            Usuario usuario = usuarioMgr.loginUser(username, password);
            System.out.println("Usuario autenticado: " + usuario.getNombre());

            if (usuario.getTipo().equals("administrador")) {
                return ResponseEntity.ok(new LoginResponse("administrador", usuario.getNombre(), usuario.getCedula()));
            } else if (usuario.getTipo().equals("turista")) {
                return ResponseEntity.ok(new LoginResponse("turista", usuario.getNombre(), usuario.getCedula()));
            } else {
                return ResponseEntity.status(403).body("Usuario o contraseña incorrectos");
            }
        } catch (EntidadNoExiste e) {
            System.out.println("Error de autenticación: " + e.getMessage());
            return ResponseEntity.status(403).body("Usuario o contraseña incorrectos");
        }
    }
}
