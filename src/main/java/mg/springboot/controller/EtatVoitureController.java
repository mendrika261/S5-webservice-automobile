package mg.springboot.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.EtatVoiture;
import mg.springboot.security.Response;
import mg.springboot.service.EtatVoitureService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Getter
@Setter
public class EtatVoitureController {
    EtatVoitureService etatVoitureService;

    public EtatVoitureController(EtatVoitureService etatVoitureService) {
        this.etatVoitureService = etatVoitureService;
    }

    @GetMapping({"/admin/etat_voitures", "/api/etat-voitures"})
    public Response<?> getEtatVoitures()
    {
        return Response.send(HttpStatus.OK,"success",etatVoitureService.getAll());
    }

    @GetMapping("/admin/etat_voitures/{id}")
    public Response<?> getEtatVoitures(@PathVariable int id)
    {
        return Response.send(HttpStatus.OK,"success",etatVoitureService.findById(id));
    }

    @PostMapping("/admin/etat_voitures")
    public Response<?> addEtatVoiture(@Valid EtatVoiture etatVoiture)
    {
        return Response.send(HttpStatus.OK,"success","L'état de voiture a été ajouté",
                etatVoitureService.save(etatVoiture));
    }

    @PutMapping("/admin/etat_voitures/{id}")
    public Response<?> modifyEtatVoiture(@PathVariable Integer id, @Valid EtatVoiture etatVoiture)
    {
        return Response.send(HttpStatus.OK,"success","L'état de voiture a été modifié",
                etatVoitureService.modify(id,etatVoiture));
    }

    @DeleteMapping("/admin/etat_voitures/{id}")
    public Response<?> deleteEtatVoiture(@PathVariable Integer id)
    {
        return Response.send(HttpStatus.OK,"success","L'état de voiture a été supprimé",
                etatVoitureService.delete(id));
    }
}
