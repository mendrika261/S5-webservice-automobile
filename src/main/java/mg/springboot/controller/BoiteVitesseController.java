package mg.springboot.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.BoiteVitesse;
import mg.springboot.entity.Pays;
import mg.springboot.security.Response;
import mg.springboot.service.BoiteVitesseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Setter
@Getter
public class BoiteVitesseController {
    BoiteVitesseService boiteVitesseService;

    public BoiteVitesseController(BoiteVitesseService boiteVitesseService) {
        this.boiteVitesseService = boiteVitesseService;
    }

    @GetMapping("/boite_vitesses")
    public Response<?> getBoiteVitesse() {
        return Response.send(HttpStatus.OK, "success", boiteVitesseService.findAll());
    }

    @GetMapping("/boite_vitesses/{id}")
    public Response<?> getBoiteVitesse(@PathVariable int id) {
        return Response.send(HttpStatus.OK, "success", boiteVitesseService.findById(id));
    }

    @PostMapping("/boite_vitesses")
    public Response<?> addBoiteVitesse(@Valid BoiteVitesse boiteVitesse) {
        return Response.send(HttpStatus.OK, "success", "La boite de vitesse a été ajouté",
                boiteVitesseService.save(boiteVitesse));
    }

    @PutMapping("/boite_vitesses/{id}")
    public Response<?> modifyBoiteVitesse(@PathVariable int id, @Valid BoiteVitesse boiteVitesse) {
        return Response.send(HttpStatus.OK, "success", "La boite de vitesse a été modifié",
                boiteVitesseService.modify(id, boiteVitesse));
    }

    @DeleteMapping("/boite_vitesses/{id}")
    public Response<?> deleteBoiteVitesse(@PathVariable int id) {
        return Response.send(HttpStatus.OK, "success", "La boite de vitesse a été supprimé",
                boiteVitesseService.delete(id));
    }
}
