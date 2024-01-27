package mg.springboot.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.Fichier;
import mg.springboot.security.Response;
import mg.springboot.service.FichierService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Setter
@Getter
public class FichierController {
    FichierService fichierService;

    public FichierController(FichierService fichierService) {
        this.fichierService = fichierService;
    }

    @GetMapping("/fichiers")
    public Response<?> getfichiers() {
        return Response.send(HttpStatus.OK, "success", fichierService.findAll());
    }

    @GetMapping("/fichiers/{id}")
    public Response<?> getfichier(@PathVariable int id) {
        return Response.send(HttpStatus.OK, "success", fichierService.findById(id));
    }

    @PostMapping("/fichiers")
    public Response<?> addfichiers(@Valid Fichier fichier) {
        return Response.send(HttpStatus.OK, "success", "Le fichier a été ajouté",
                fichierService.save(fichier));
    }

    @DeleteMapping("/fichiers/{id}")
    public Response<?> deletefichier(@PathVariable int id) {
        return Response.send(HttpStatus.OK, "success", "Le fichier a été supprimé", fichierService.delete(id));
    }
}
