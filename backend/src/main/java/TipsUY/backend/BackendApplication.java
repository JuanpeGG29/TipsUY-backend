package TipsUY.backend;

import TipsUY.backend.entities.Foto;
import TipsUY.backend.entities.Lugar;
import TipsUY.backend.entities.Ubicacion;
import TipsUY.backend.entities.Usuario;
import TipsUY.backend.services.FotoMgr;
import TipsUY.backend.services.LugarMgr;
import TipsUY.backend.services.UbicacionMgr;
import TipsUY.backend.services.UsuarioMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class BackendApplication {

	@Autowired
	private LugarMgr lugarmgr;

	@Autowired
	private FotoMgr fotoMgr;

	@Autowired
	private UbicacionMgr ubicacionMgr;

	@Autowired
	private UsuarioMgr usuarioMgr;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo() {
		return (args) -> {
			// Crear lugares y fotos de prueba
			crearLugaresDePrueba();
		};
	}

	private void crearLugaresDePrueba() {
		try {
			System.out.println("ENtro a intentar crear los lugares");
			// Crear lugar Plaza Virgilio
			Lugar plazaVirgilio = new Lugar(
					"Plaza Virgilio",
					"Una playa increíble con vistas únicas al río y un parque en Montevideo",
					"Ciudad",
					"Todo el día",
					"Entrada libre",
					"555-1234",
					4.8
			);
			lugarmgr.addLugar(plazaVirgilio);

			// Crear ubicación para Plaza Virgilio
			Ubicacion ubicacionPlaza = new Ubicacion(plazaVirgilio, -34.901112, -56.164532, "Montevideo, Uruguay");
			ubicacionMgr.addUbicacion(ubicacionPlaza);

			// Crear foto para Plaza Virgilio
			Foto fotoPlaza = new Foto(null, plazaVirgilio, "/C:/GENERAL/UM general/Sistemas Distribuidos/images/PlazaVirgilio/plaza-virgilio.jpeg", LocalDate.now());
			fotoMgr.addFoto(fotoPlaza);

			// Crear lugar Kfe
			Lugar kfe = new Lugar(
					"Kfe",
					"Un café acogedor en pleno corazón de la ciudad",
					"Restaurante",
					"8:00 AM - 10:00 PM",
					"Precios moderados",
					"555-5678",
					4.5
			);
			lugarmgr.addLugar(kfe);

			// Crear foto para Kfe
			Foto fotoKfe = new Foto(null, kfe, "/C:/GENERAL/UM general/Sistemas Distribuidos/images/Kfe/kfe.png", LocalDate.now());
			fotoMgr.addFoto(fotoKfe);

			System.out.println("Se crearon los lugares");

			Usuario turista = new Usuario("Juan", "juanpedroggse@gmail.com", "12345", "52750998", "turista", LocalDate.now());
			usuarioMgr.addUsuario(turista);
			Usuario admin = new Usuario("Manu", "manselmi@correo.um.edu.uy", "12345", "69999999", "administrador", LocalDate.now());
			usuarioMgr.addUsuario(admin);

			System.out.println("Lugares y usuarios de prueba creados correctamente.");
		} catch (Exception e) {
			System.err.println("Error creando lugares de prueba: " + e.getMessage());
		}
	}
}
