package mg.springboot.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.Modele;
import mg.springboot.entity.Utilisateur;
import mg.springboot.security.Response;
import mg.springboot.service.ModeleService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Setter
@Getter
public class ModeleController {
    ModeleService modeleService;

    public ModeleController(ModeleService modeleService) {
        this.modeleService = modeleService;
    }

    @GetMapping("/modeles")
    public Response<?> getModeles() {
        return Response.send(HttpStatus.OK, "success", modeleService.findAll());
    }

    @GetMapping("/modeles/{id}")
    public Response<?> getModeles(@PathVariable int id) {
        return Response.send(HttpStatus.OK, "success", modeleService.findById(id));
    }

    @PostMapping("/modeles")
    public Response<?> addModeles(@Valid Modele modele) {
        return Response.send(HttpStatus.OK, "success", "Le modele a été ajouté",
                modeleService.save(modele));
    }

    @PutMapping("/modeles/{id}")
    public Response<?> modifyModele(@PathVariable int id, @Valid Modele modele) {
        return Response.send(HttpStatus.OK, "success", "Le modele a été modifié",
                modeleService.modify(id, modele));
    }

    @DeleteMapping("/modeles/{id}")
    public Response<?> deleteModele(@PathVariable int id) {
        return Response.send(HttpStatus.OK, "success", "Le modele a été supprimé",
                modeleService.delete(id));
    }
}
