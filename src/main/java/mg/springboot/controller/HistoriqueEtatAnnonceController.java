package mg.springboot.controller;

import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.Annonce;
import mg.springboot.security.Response;
import mg.springboot.service.AnnonceService;
import mg.springboot.service.HistoriqueEtatAnnonceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Setter
@Getter
@RequestMapping("/admin")
public class HistoriqueEtatAnnonceController {
    HistoriqueEtatAnnonceService historiqueEtatAnnonceService;
    AnnonceService annonceService;

    public HistoriqueEtatAnnonceController(HistoriqueEtatAnnonceService historiqueEtatAnnonceService, AnnonceService annonceService) {
        this.historiqueEtatAnnonceService = historiqueEtatAnnonceService;
        this.annonceService = annonceService;
    }

    @GetMapping("/historiques_etats_annonces/{id}")
    public Response<?> findAll(@PathVariable("id") int id) {
        Annonce annonce=annonceService.findById(id);
        if(annonce==null)
        {
            return Response.send(HttpStatus.OK, "error","Annnonce inexistante" );
        }
        return Response.send(HttpStatus.OK, "success", historiqueEtatAnnonceService.getAllByAnnonce(annonce));
    }
}
