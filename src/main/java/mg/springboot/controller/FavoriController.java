package mg.springboot.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.Annonce;
import mg.springboot.entity.Favori;
import mg.springboot.repository.AnnonceRepository;
import mg.springboot.security.Response;
import mg.springboot.security.Token;
import mg.springboot.service.AnnonceService;
import mg.springboot.service.FavoriService;
import mg.springboot.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Getter
@Setter
public class FavoriController {
    FavoriService favoriService;
    TokenService tokenService;
    AnnonceService annonceService;
    private final AnnonceRepository annonceRepository;

    public FavoriController(FavoriService favoriService, TokenService tokenService, AnnonceService annonceService,
                            AnnonceRepository annonceRepository) {
        this.favoriService = favoriService;
        this.tokenService = tokenService;
        this.annonceService = annonceService;
        this.annonceRepository = annonceRepository;
    }

    @GetMapping("/api/favoris")
    public Response<?> findAll(HttpServletRequest request)
    {
        Token token=tokenService.getToken(request);
        if(token==null)
        {
            return Response.send(HttpStatus.OK, "error", "aucun utilisateur connecte");
        }
        return Response.send(HttpStatus.OK, "success", favoriService.findAll(token.getUtilisateur()));
    }

    @PostMapping("/api/favoris/{id}")
    public Response<?> addFavorite(@PathVariable("id") int id, HttpServletRequest request)
    {
        Token token=tokenService.getToken(request);
        if(token==null)
        {
            return Response.send(HttpStatus.OK, "error", "aucun utilisateur connecte");
        }
        Optional<Annonce> annonce=annonceRepository.findById(id);
        if(annonce.isEmpty())
        {
            return Response.send(HttpStatus.OK, "error", "annonce non existante");
        }
        Favori favori=null;
        try {
            favori=favoriService.addFavorite(token.getUtilisateur(),annonce.get());
        }catch (RuntimeException e)
        {
            return Response.send(HttpStatus.OK, "error", e.getMessage());

        }
        return Response.send(HttpStatus.OK, "success","Annonce ajoutée a vos favoris",favori);
    }


    @DeleteMapping("/api/favoris/{id}")
    public Response<?> removeFavorite(@PathVariable("id") int id, HttpServletRequest request)
    {
        Token token=tokenService.getToken(request);
        if(token==null)
        {
            return Response.send(HttpStatus.OK, "error", "aucun utilisateur connecte");
        }
        Annonce annonce=annonceService.findById(id);
        Favori favori=null;
        try {
            favori=favoriService.removeFavorite(token.getUtilisateur(),annonce);
        }catch (RuntimeException e)
        {
            return Response.send(HttpStatus.OK, "error", e.getMessage());

        }

        return Response.send(HttpStatus.OK, "success","Annonce retiree de vos favoris", favori);
    }
}
