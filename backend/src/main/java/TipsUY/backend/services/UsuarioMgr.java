package TipsUY.backend.services;

import TipsUY.backend.entities.Usuario;
import TipsUY.backend.exceptions.EntidadNoExiste;
import TipsUY.backend.exceptions.EntidadYaExiste;
import TipsUY.backend.persistence.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UsuarioMgr {
    @Autowired
    private UsuarioRepository usuarioRepository;
    public Usuario loginUser(String email, String contrasena) throws EntidadNoExiste {
        System.out.println(email +  " " +  contrasena);
        Usuario foundUsuario = usuarioRepository.findByEmail(email);
        System.out.println(foundUsuario.getNombre() + " " + foundUsuario.getPassword());
        if (foundUsuario != null && foundUsuario.getPassword().equals(contrasena)) {
            System.out.println("ACCEDIO");
            return foundUsuario;
        }
        throw new EntidadNoExiste("Email o contraseña incorrectos");
    }

    public Usuario crearUsuario(Usuario usuario) throws EntidadNoExiste {
        String nombre=usuario.getNombre();
        String password=usuario.getPassword();
        String email=usuario.getEmail();
        String cedula= usuario.getCedula();
        String tipo=usuario.getTipo();

        Usuario usuarioNuevo= new Usuario(nombre, email, password, cedula, tipo, LocalDate.now());
        return usuarioNuevo;
    }

    public void addUsuario(Usuario usuario) throws EntidadYaExiste {
        if(usuarioRepository.findByEmail(usuario.getEmail())!=null){
            throw new EntidadYaExiste("Ya existe un Usuario con ese email");
        }
        usuarioRepository.save(usuario);
    }
}
