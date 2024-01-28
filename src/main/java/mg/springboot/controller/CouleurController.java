package mg.springboot.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.Couleur;
import mg.springboot.entity.Energie;
import mg.springboot.security.Response;
import mg.springboot.service.CouleurService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Setter
@Getter
@RequestMapping("/admin")
public class CouleurController {
    CouleurService couleurService;

    public CouleurController(CouleurService couleurService) {
        this.couleurService = couleurService;
    }

    @GetMapping("/couleurs")
    public Response<?> getcouleurs() {
        return Response.send(HttpStatus.OK, "success", couleurService.findAll());
    }

    @GetMapping("/couleurs/{id}")
    public Response<?> getcouleurs(@PathVariable int id) {
        return Response.send(HttpStatus.OK, "success", couleurService.findById(id));
    }

    @PostMapping("/couleurs")
    public Response<?> addcouleurs(@Valid Couleur couleur) {
        return Response.send(HttpStatus.OK, "success", "La couleur a été ajouté",
                couleurService.save(couleur));
    }

    @PutMapping("/couleurs/{id}")
    public Response<?> modifycouleurs(@PathVariable int id, @Valid Couleur couleur) {
        return Response.send(HttpStatus.OK, "success", "La couleur a été modifié",
                couleurService.modify(id, couleur));
    }

    @DeleteMapping("/couleurs/{id}")
    public Response<?> deletecouleurs(@PathVariable int id) {
        return Response.send(HttpStatus.OK, "success", "La couleur a été supprimé",
                couleurService.delete(id));
    }
}
