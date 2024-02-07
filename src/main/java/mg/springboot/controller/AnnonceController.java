package mg.springboot.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import mg.springboot.entity.*;
import mg.springboot.exception.AccessDeniedException;
import mg.springboot.security.Response;
import mg.springboot.security.Token;
import mg.springboot.service.AnnonceService;
import mg.springboot.service.EtatVoitureService;
import mg.springboot.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class AnnonceController {
    private final AnnonceService annonceService;
    private final TokenService tokenService;
    private final EtatVoitureService etatVoitureService;



    public AnnonceController(AnnonceService annonceService, TokenService tokenService, EtatVoitureService etatVoitureService) {
        this.annonceService = annonceService;
        this.tokenService = tokenService;
        this.etatVoitureService = etatVoitureService;
    }

    @GetMapping("/admin/annonces")
    public Response<?> findAll() {
        return Response.send(HttpStatus.OK, "success", annonceService.findAll());
    }

    @GetMapping("/api/annonces/filter")
    public Response<?> findFilter(@RequestBody FilterRequest filterRequest) {
        return Response.send(HttpStatus.OK, "success", annonceService.test_filter(filterRequest));
    }

    @GetMapping("/admin/annonces/en-attente")
    public Response<?> findAllEnAttente() {
        return Response.send(HttpStatus.OK, "success", annonceService.findAllEnAttente());
    }

    @GetMapping("/annonces")
    public Response<?> findAllValide(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "ASC") String order) {
        return Response.send(HttpStatus.OK, "success", annonceService.findAllValides(page, size, sort, order));
    }

    @GetMapping("/api/annonces/{id}")
    public Response<?> findByIdPourUtilisateur(HttpServletRequest httpServletRequest, @PathVariable Integer id) {
        Token token = tokenService.getToken(httpServletRequest);
        Annonce annonce = annonceService.findById(id);
        if(annonce.getEtat() == Annonce.ETAT_VALIDE || annonce.getVoiture().getUtilisateur().getId().equals(token.getUtilisateur().getId()))
            return Response.send(HttpStatus.OK, "success", annonceService.findById(id));
        throw new AccessDeniedException("Vous n'avez pas le droit de voir cette annonce");
    }

    @GetMapping("/api/utilisateurs/{id}/annonces")
    public Response<?> findByIdUtilisateur(HttpServletRequest httpServletRequest, @PathVariable String id) {
        Token token = tokenService.getToken(httpServletRequest);
        if(token.getUtilisateur().getId().equals(id) || id.equalsIgnoreCase("undefined"))
            return Response.send(HttpStatus.OK, "success", annonceService.findAllByIdUtilisateur(token.getUtilisateur().getId()));
        return Response.send(HttpStatus.OK, "success", annonceService.findAllByIdUtilisateurAndValide(id));
    }

    @PutMapping("/api/annonces/{id}")
    public Response<?> modifierEtat(HttpServletRequest httpServletRequest, @PathVariable Integer id, Integer etat) {
        Token token = tokenService.getToken(httpServletRequest);
        Annonce annonceR = annonceService.findById(id);
        if(!annonceR.getVoiture().getUtilisateur().getId().equals(token.getUtilisateur().getId()))
            throw new AccessDeniedException("Vous n'avez pas le droit de modifier cette annonce");
        return Response.send(HttpStatus.OK, "success", annonceService.modifierEtat(id, etat));
    }

    @DeleteMapping("/api/annonces/{id}")
    public Response<?> delete(HttpServletRequest httpServletRequest, @PathVariable Integer id) {
        Token token = tokenService.getToken(httpServletRequest);
        Annonce annonceR = annonceService.findById(id);
        if(!annonceR.getVoiture().getUtilisateur().getId().equals(token.getUtilisateur().getId()))
            throw new AccessDeniedException("Vous n'avez pas le droit de supprimer cette annonce");
        return Response.send(HttpStatus.OK, "success", annonceService.delete(id));
    }

    @GetMapping("/admin/annonces/{id}")
    public Response<?> findById(@PathVariable Integer id) {
        return Response.send(HttpStatus.OK, "success", annonceService.findById(id));
    }

    @PutMapping("/admin/annonces/{id}/valider")
    public Response<?> valider(HttpServletRequest httpServletRequest, @PathVariable Integer id) {
        Token token = tokenService.getToken(httpServletRequest);
        return Response.send(HttpStatus.OK, "success", annonceService.valider(id, token.getUtilisateur()));
    }

    @PutMapping("/admin/annonces/{id}/refuser")
    public Response<?> refuser(HttpServletRequest httpServletRequest, @PathVariable Integer id) {
        Token token = tokenService.getToken(httpServletRequest);
        return Response.send(HttpStatus.OK, "success", annonceService.refuser(id, token.getUtilisateur()));
    }

    @DeleteMapping("/admin/annonces/{id}")
    public Response<?> delete(@PathVariable Integer id) {
        return Response.send(HttpStatus.OK, "success", annonceService.delete(id));
    }

    @PostMapping("/admin/annonces")
    public Response<?> save(@Valid Annonce annonce) {
        return Response.send(HttpStatus.OK, "success", annonceService.save(annonce));
    }

    @PostMapping("/api/utilisateurs/{idUtilisateur}/annonces")
    public Response<?> saveUtilisateur(@PathVariable String idUtilisateur, @Valid Annonce annonce) {
        if (!annonce.getVoiture().getUtilisateur().getId().equals(idUtilisateur))
            throw new AccessDeniedException("Vous n'avez pas le droit de créer une annonce pour un autre utilisateur");
        return Response.send(HttpStatus.OK, "success", annonceService.save(annonce));
    }

    @PutMapping("/admin/annonces/{id}")
    public Response<?> modify(@PathVariable Integer id, @Valid Annonce annonce) {
        return Response.send(HttpStatus.OK, "success", annonceService.modify(id, annonce));
    }
}
