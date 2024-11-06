package TipsUY.backend;

import TipsUY.backend.entities.*;
import TipsUY.backend.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class BackendApplication {

	@Autowired
	private LugarMgr lugarmgr;

	@Autowired
	private ComentarioMgr comentarioMgr;

	@Autowired
	private FotoMgr fotoMgr;

	@Autowired
	private UbicacionMgr ubicacionMgr;

	@Autowired
	private UsuarioMgr usuarioMgr;

	@Value("${ibm.cos.endpoint}")
	private String endpoint;

	@Value("${ibm.cos.accessKey}")
	private String accessKey;

	@Value("${ibm.cos.secretKey}")
	private String secretKey;

	@Value("${ibm.cos.bucketName}")
	private String bucketName;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public IBMCloudStorageService ibmCloudStorageService() {
		return new IBMCloudStorageService(endpoint, accessKey, secretKey);
	}

	@Bean
	public CommandLineRunner demo() {
		return (args) -> {
			// Crear una instancia manual de IBMCloudStorageService
			IBMCloudStorageService ibmCloudStorageService = new IBMCloudStorageService(endpoint, accessKey, secretKey);

			// Crear lugares y fotos de prueba
			crearLugaresDePrueba();

			// Imprimir los nombres de todos los objetos en el bucket
			System.out.println("Objetos en el bucket:");
			List<String> fileNames = ibmCloudStorageService.listFileNames(bucketName);
			fileNames.forEach(System.out::println);
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
			Foto fotoPlaza = new Foto(null, plazaVirgilio, "https://bucket-demos.s3.br-sao.cloud-object-storage.appdomain.cloud/PlazaVirgilio:plaza-virgilio.jpeg", LocalDate.now());
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
			Foto fotoKfe = new Foto(null, kfe, "https://bucket-demos.s3.br-sao.cloud-object-storage.appdomain.cloud/Kfe:Kfe.png", LocalDate.now());
			fotoMgr.addFoto(fotoKfe);

			System.out.println("Se crearon los lugares");

			Usuario turista = new Usuario("Juan", "juanpedroggse@gmail.com", "12345", "52750998", "turista", LocalDate.now());
			usuarioMgr.addUsuario(turista);
			Usuario admin = new Usuario("Manu", "manselmi@correo.um.edu.uy", "12345", "69999999", "administrador", LocalDate.now());
			usuarioMgr.addUsuario(admin);


			// Crear comentarios de prueba para cada lugar
			Comentario comentario1 = new Comentario(turista, plazaVirgilio, "Excelente lugar para relajarse y disfrutar de la vista.", 5);
			Comentario comentario2 = new Comentario(admin, plazaVirgilio, "Hermoso paisaje y muy tranquilo.", 4);
			comentarioMgr.addComentario(comentario1);
			comentarioMgr.addComentario(comentario2);

			Comentario comentario3 = new Comentario(turista, kfe, "El café es delicioso y el ambiente acogedor.", 5);
			Comentario comentario4 = new Comentario(admin, kfe, "Un excelente lugar para pasar la tarde.", 4);
			comentarioMgr.addComentario(comentario3);
			comentarioMgr.addComentario(comentario4);


			System.out.println("Lugares y usuarios de prueba creados correctamente.");
		} catch (Exception e) {
			System.err.println("Error creando lugares de prueba: " + e.getMessage());
		}
	}
}
