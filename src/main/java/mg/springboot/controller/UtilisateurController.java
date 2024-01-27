package mg.springboot.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.Utilisateur;
import mg.springboot.security.Response;
import mg.springboot.security.Token;
import mg.springboot.service.TokenService;
import mg.springboot.service.UtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Getter
@Setter
@RestController
public class UtilisateurController {
    private final UtilisateurService utilisateurService;
    private final TokenService tokenService;

    public UtilisateurController(UtilisateurService utilisateurRepository,
                                 TokenService tokenRepository) {
        this.utilisateurService = utilisateurRepository;
        this.tokenService = tokenRepository;
    }

    @PostMapping("/connexion")
    public Response<?> connexion(String email, String motDePasse) {
        Optional<Utilisateur> utilisateur = utilisateurService.findByEmailAndMotDePasse(email, motDePasse);
        if (utilisateur.isPresent())
            return Response.send(HttpStatus.OK, "success","Connexion réussie", tokenService.createFor(utilisateur.get()));
        return Response.send(HttpStatus.OK, "error", "Login ou mot de passe incorrect");
    }

    @PostMapping("/inscription")
    public Response<?> inscription(@Valid Utilisateur utilisateur) {
        if(utilisateur.getLevel() != Utilisateur.LEVEL_USER)
            return Response.send(HttpStatus.OK, "error", "Vous ne pouvez s'incrire que comme utilisateur");
        return Response.send(HttpStatus.OK, "success", "Inscription réussie", utilisateurService.save(utilisateur));
    }

    @DeleteMapping("/deconnexion")
    public Response<?> deconnexion(HttpServletRequest request) {
        Token token = tokenService.getToken(request);
        tokenService.delete(token);
        return Response.send(HttpStatus.OK, "success", "Déconnexion réussie", token);
    }

    @GetMapping("/utilisateurs")
    public Response<?> getUtilisateurs() {
        return Response.send(HttpStatus.OK, "success", utilisateurService.findAll());
    }

    @GetMapping("/utilisateurs/{id}")
    public Response<?> getUtilisateurs(@PathVariable String id) {
        return Response.send(HttpStatus.OK, "success", utilisateurService.findById(id));
    }

    @PostMapping("/utilisateurs")
    public Response<?> addUtilisateur(@Valid Utilisateur utilisateur) {
        return Response.send(HttpStatus.OK, "success", "L'utilisateur a été ajouté",
                    utilisateurService.save(utilisateur));
    }

    @PutMapping("/utilisateurs/{id}")
    public Response<?> modifyUtilisateur(@PathVariable String id, Utilisateur utilisateur) {
        return Response.send(HttpStatus.OK, "success", "L'utilisateur a été modifié",
                    utilisateurService.modify(id, utilisateur));
    }

    @DeleteMapping("/utilisateurs/{id}")
    public Response<?> deleteUtilisateur(@PathVariable String id) {
        return Response.send(HttpStatus.OK, "success", "L'utilisateur a été supprimé",
                    utilisateurService.delete(id));
    }

    @DeleteMapping("/utilisateurs/{id}/photo/{photoId}")
    public Response<?> deletePhoto(@PathVariable String id, @PathVariable Integer photoId) {
        return Response.send(HttpStatus.OK, "success", "La photo a été supprimée",
                    utilisateurService.deletePhoto(id, photoId));
    }

    @PutMapping("/utilisateurs/{id}/photo/{photoId}")
    public Response<?> modifyPhoto(@PathVariable String id, @PathVariable Integer photoId) {
        return Response.send(HttpStatus.OK, "success", "La photo a été modifiée",
                    utilisateurService.modifyPhoto(id, photoId));
    }

    @PutMapping("/utilisateurs/{id}/mot-de-passe")
    public Response<?> modifyMotDePasse(@PathVariable String id, String motDePasse, String nouveauMotDePasse) {
        return Response.send(HttpStatus.OK, "success", "Le mot de passe a été modifié",
                    utilisateurService.modifyMotDePasse(id, motDePasse, nouveauMotDePasse));
    }
}
