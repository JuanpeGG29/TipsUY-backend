package TipsUY.backend.controllers;

import TipsUY.backend.entities.Lugar;
import TipsUY.backend.persistence.LugarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class LugarController {

    @Autowired
    private LugarRepository lugarRepository;

    @GetMapping("/lugares")
    public List<Lugar> obtenerLugares() {
        System.out.println("ENTRO CONTROLLER lugares");
        return lugarRepository.findAll();
    }
}