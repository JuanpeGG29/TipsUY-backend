package TipsUY.backend.controllers;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final String imageDirectory = "C:/GENERAL/UM general/Sistemas Distribuidos/images/"; // Ajusta la ruta aquí

    @GetMapping("/{placeName}/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String placeName, @PathVariable String imageName) {
        System.out.println("Entro Controller image: " + placeName + imageName);
        try {
            System.out.println("ENTRO AL TRY");
            Path filePath = Paths.get(imageDirectory, placeName).resolve(imageName).normalize();
            System.out.println(filePath);
            Resource resource = new UrlResource(filePath.toUri());
            System.out.println(resource +": " + resource.getFilename());

            if (resource.exists() && resource.isReadable()) {
                System.out.println("ENCONTRO EL RECURSO");
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // Ajusta si tus imágenes no son JPEG
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + imageName + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}