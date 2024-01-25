package mg.springboot.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.Paiement;
import mg.springboot.security.Response;
import mg.springboot.service.PaiementService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Getter
@Setter
public class PaiementController {
    PaiementService paiementService;

    public PaiementController(PaiementService paiementService) {
        this.paiementService = paiementService;
    }

    @GetMapping("/paiements")
    public Response<?> getPaiements()
    {
        return Response.send(HttpStatus.OK,"success",paiementService.getAll());
    }

    @GetMapping("/paiements/{id}")
    public Response<?> getPaiements(@PathVariable int id)
    {
        return Response.send(HttpStatus.OK,"success",paiementService.findById(id));
    }

    @PostMapping("/paiements")
    public Response<?> addPaiement(@Valid Paiement paiement)
    {
        return Response.send(HttpStatus.OK,"success","Le mode de paiement a été ajouté",
                paiementService.save(paiement));
    }

    @PutMapping("/paiements/{id}")
    public Response<?> modifyPaiement(@PathVariable Integer id, @Valid Paiement paiement)
    {
        return Response.send(HttpStatus.OK,"success","Le mode de paiement a été modifié",
                paiementService.modify(id,paiement));
    }

    @DeleteMapping("/paiements/{id}")
    public Response<?> deletePaiement(@PathVariable Integer id)
    {
        return Response.send(HttpStatus.OK,"success","Le mode de paiement a été supprimé",
                paiementService.delete(id));
    }
}
