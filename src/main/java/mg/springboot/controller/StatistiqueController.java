package mg.springboot.controller;

import lombok.Getter;
import lombok.Setter;
import mg.springboot.security.GraphResponse;
import mg.springboot.security.Response;
import mg.springboot.service.VoitureService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Getter
@Setter
@RequestMapping("/admin")
public class StatistiqueController {
    private final VoitureService voitureService;

    public StatistiqueController(VoitureService voitureService) {
        this.voitureService = voitureService;
    }

    @GetMapping("/voitures/boite-vitesses/stats")
    public Response<?> getStatsBoiteVitesseVoiture() {
        return Response.send(HttpStatus.OK, "success",
                new GraphResponse(voitureService.getStatsBoiteVitesseVoiture(), "Nombre de voiture"));
    }

    @GetMapping("/voitures/couleurs/stats")
    public Response<?> getStatsCouleurVoiture() {
        return Response.send(HttpStatus.OK, "success",
                new GraphResponse(voitureService.getStatsCouleurVoiture(), "Nombre de voiture"));
    }

    @GetMapping("/voitures/energies/stats")
    public Response<?> getStatsEnergieVoiture() {
        return Response.send(HttpStatus.OK, "success",
                new GraphResponse(voitureService.getStatsEnergieVoiture(), "Nombre de voiture"));
    }

    @GetMapping("/voitures/etat/stats")
    public Response<?> getStatsEtatVoiture() {
        return Response.send(HttpStatus.OK, "success",
                new GraphResponse(voitureService.getStatsEtatVoiture(), "Nombre de voiture"));
    }

    @GetMapping("/voitures/pays/stats")
    public Response<?> getStatsPaysVoiture() {
        return Response.send(HttpStatus.OK, "success",
                new GraphResponse(voitureService.getStatsPaysVoiture(), "Nombre de voiture"));
    }

    @GetMapping("/voitures/annees/stats")
    public Response<?> getStatsAnneeVoiture() {
        return Response.send(HttpStatus.OK, "success",
                new GraphResponse(voitureService.getStatsAnneeVoiture(), "Nombre de voiture"));
    }

    @GetMapping("/voitures/modeles/stats")
    public Response<?> getStatsModeleVoiture() {
        return Response.send(HttpStatus.OK, "success",
                new GraphResponse(voitureService.getStatsModeleVoiture(), "Nombre de voiture"));
    }

    @GetMapping("/voitures/marques/stats")
    public Response<?> getStatsMarqueVoiture() {
        return Response.send(HttpStatus.OK, "success",
                new GraphResponse(voitureService.getStatsMarqueVoiture(), "Nombre de voiture"));
    }

    @GetMapping("/voitures/annonces/stats")
    public Response<?> getStatsNbAnnonce() {
        return Response.send(HttpStatus.OK, "success",
                new GraphResponse(voitureService.getStatsNbAnnonce(), "Nombre d'annonce"));
    }

    @GetMapping("/voitures/chiffre-affaires/stats")
    public Response<?> getChiffreAffaire12DernierMois() {
        return Response.send(HttpStatus.OK, "success",
                new GraphResponse(voitureService.getChiffreAffaire12DernierMois(), "Chiffre d'affaire en MGA"));
    }

    @GetMapping("/utilisateurs/en-ligne/stats")
    public Response<?> getStatsUtilisateurEnLigne() {
        return Response.send(HttpStatus.OK, "success",
                voitureService.getStatsUtilisateurEnLigne());
    }

    @GetMapping("/annonces/en-attente/stats")
    public Response<?> getStatsAnnonceEnAttente() {
        return Response.send(HttpStatus.OK, "success",
                voitureService.getStatsAnnonceEnAttente());
    }

    @GetMapping("/voitures/ventes/stats")
    public Response<?> getStatsNbVente() {
        return Response.send(HttpStatus.OK, "success",
                voitureService.getStatsNbVente());
    }

    @GetMapping("/annonces/expire/stats")
    public Response<?> getStatsValidationAnnonce() {
        return Response.send(HttpStatus.OK, "success",
                voitureService.getStatsValidationAnnonce());
    }

    @GetMapping("utilisateurs/stats/nombre")
    public Response<?> getStatsNombreUtilisateur() {
        return Response.send(HttpStatus.OK, "success",
                voitureService.getNbUtilisateur());
    }

    @GetMapping("voitures/stats/nombre")
    public Response<?> getStatsPremiereMain() {
        return Response.send(HttpStatus.OK, "success",
                voitureService.getNbVoiture());
    }

    @GetMapping("annonces/stats/nombre")
    public Response<?> getStatsAnnonce() {
        return Response.send(HttpStatus.OK, "success",
                voitureService.getNbAnnonce());
    }

    @GetMapping("ventes/stats/nombre")
    public Response<?> getStatsNbVenteAnnonce() {
        return Response.send(HttpStatus.OK, "success",
                voitureService.getNbVente());
    }
}
