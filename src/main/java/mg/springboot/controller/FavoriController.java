package mg.springboot.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.Annonce;
import mg.springboot.entity.Favori;
import mg.springboot.security.Response;
import mg.springboot.security.Token;
import mg.springboot.service.AnnonceService;
import mg.springboot.service.FavoriService;
import mg.springboot.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Getter
@Setter
@RequestMapping("/admin")
public class FavoriController {
    FavoriService favoriService;
    TokenService tokenService;
    AnnonceService annonceService;

    public FavoriController(FavoriService favoriService, TokenService tokenService, AnnonceService annonceService) {
        this.favoriService = favoriService;
        this.tokenService = tokenService;
        this.annonceService = annonceService;
    }

    @GetMapping("/favoris")
    public Response<?> findAll(HttpServletRequest request)
    {
        Token token=tokenService.getToken(request);
        if(token==null)
        {
            return Response.send(HttpStatus.OK, "error", "aucun utilisateur connecte");
        }
        return Response.send(HttpStatus.OK, "success", favoriService.findAll(token.getUtilisateur()));
    }

    @GetMapping("/favoris/{id}")
    public Response<?> addFavorite(@PathVariable("id") int id, HttpServletRequest request)
    {
        Token token=tokenService.getToken(request);
        if(token==null)
        {
            return Response.send(HttpStatus.OK, "error", "aucun utilisateur connecte");
        }
        Annonce annonce=annonceService.findById(id);
        Favori favori=null;
        try {
            favori=favoriService.addFavorite(token.getUtilisateur(),annonce);
        }catch (RuntimeException e)
        {
            return Response.send(HttpStatus.OK, "error", e.getMessage());

        }
        return Response.send(HttpStatus.OK, "success","Annonce ajoutee a vos favoris",favori);
    }


    @DeleteMapping("/favoris/{id}")
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
