package mg.springboot.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import mg.springboot.entity.Annonce;
import mg.springboot.security.Response;
import mg.springboot.security.Token;
import mg.springboot.service.AnnonceService;
import mg.springboot.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AnnonceController {
    private final AnnonceService annonceService;
    private final TokenService tokenService;

    public AnnonceController(AnnonceService annonceService, TokenService tokenService) {
        this.annonceService = annonceService;
        this.tokenService = tokenService;
    }

    @GetMapping("/annonces")
    public Response<?> findAll() {

        return Response.send(HttpStatus.OK, "success", annonceService.findAll());
    }

    @GetMapping("/annonces/en-attente")
    public Response<?> findAllEnAttente() {
        return Response.send(HttpStatus.OK, "success", annonceService.findAllEnAttente());
    }

    @GetMapping("/annonces/valide")
    public Response<?> findAllValide(HttpServletRequest request) {
        List<Annonce> annonces=null;
        if(tokenService.getToken(request)!=null)
        {
            Token token=tokenService.getToken(request);
            annonces=annonceService.findAllValides(token.getUtilisateur());

        }
        else
        {
            annonces=annonceService.findAllValides();
        }
        return Response.send(HttpStatus.OK, "success", annonces);
    }

    @GetMapping("/annonces/{id}")
    public Response<?> findById(@PathVariable Integer id) {
        return Response.send(HttpStatus.OK, "success", annonceService.findById(id));
    }

    @PutMapping("/annonces/{id}/valider")
    public Response<?> valider(HttpServletRequest httpServletRequest, @PathVariable Integer id) {
        Token token = tokenService.getToken(httpServletRequest);
        return Response.send(HttpStatus.OK, "success", annonceService.valider(id, token.getUtilisateur()));
    }

    @PutMapping("/annonces/{id}/refuser")
    public Response<?> refuser(HttpServletRequest httpServletRequest, @PathVariable Integer id) {
        Token token = tokenService.getToken(httpServletRequest);
        return Response.send(HttpStatus.OK, "success", annonceService.refuser(id, token.getUtilisateur()));
    }

    @DeleteMapping("/annonces/{id}")
    public Response<?> delete(@PathVariable Integer id) {
        return Response.send(HttpStatus.OK, "success", annonceService.delete(id));
    }

    @PostMapping("/annonces")
    public Response<?> save(@Valid Annonce annonce) {
        return Response.send(HttpStatus.OK, "success", annonceService.save(annonce));
    }

    @PutMapping("/annonces/{id}")
    public Response<?> modify(@PathVariable Integer id, @Valid Annonce annonce) {
        return Response.send(HttpStatus.OK, "success", annonceService.modify(id, annonce));
    }
}
