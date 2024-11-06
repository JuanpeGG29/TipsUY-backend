package TipsUY.backend.controllers;

import TipsUY.backend.services.IBMCloudStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "http://localhost:3000")
public class ImageController {

    private final IBMCloudStorageService ibmCloudStorageService;

    @Autowired
    public ImageController(IBMCloudStorageService ibmCloudStorageService) {
        this.ibmCloudStorageService = ibmCloudStorageService;
    }

    @GetMapping("/{placeName}/{imageName}")
    public ResponseEntity<String> getImageUrl(@PathVariable String placeName, @PathVariable String imageName) {
        String bucketName = "bucket-demos"; // Asegúrate de que sea el nombre correcto de tu bucket
        System.out.println(placeName);
        String fileName = imageName;
        System.out.println(fileName);
        String fileUrl = ibmCloudStorageService.getFileUrl(bucketName, fileName);
        System.out.println(fileUrl);
        return ResponseEntity.ok(fileUrl);
    }
}