package mg.springboot.controller;

import jakarta.servlet.http.HttpServletRequest;
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
            return Response.send(HttpStatus.OK, "success", tokenService.createFor(utilisateur.get()));
        return Response.send(HttpStatus.OK, "error", "Login ou mot de passe incorrect");
    }

    @GetMapping("/deconnexion")
    public Response<?> deconnexion(HttpServletRequest request) {
        Token token = tokenService.getToken(request);
        tokenService.delete(token);
        return Response.send(HttpStatus.OK, "success", "Déconnexion réussie", token);
    }

    @GetMapping("/utilisateurs")
    public Response<?> getUtilisateurs() {
        return Response.send(HttpStatus.OK, "success", utilisateurService.findAll());
    }

    @PostMapping("/utilisateurs")
    public Response<?> addUtilisateur(Utilisateur utilisateur) {
        try {
            return Response.send(HttpStatus.OK, "success", "Utilisateur ajouté", utilisateurService.save(utilisateur));
        } catch (Exception e) {
            return Response.send(HttpStatus.OK, "error", e.getMessage(), utilisateur);
        }
    }

    @PutMapping("/utilisateurs")
    public Response<?> modifyUtilisateur(Utilisateur utilisateur) {
        try {
            return Response.send(HttpStatus.OK, "success", "Utilisateur modifié", utilisateurService.modify(utilisateur));
        } catch (Exception e) {
            return Response.send(HttpStatus.OK, "error", e.getMessage(), utilisateur);
        }
    }

    @DeleteMapping("/utilisateurs")
    public Response<?> deleteUtilisateur(String id) {
        try {
            return Response.send(HttpStatus.OK, "success", "Utilisateur supprimé", utilisateurService.delete(id));
        } catch (Exception e) {
            return Response.send(HttpStatus.OK, "error", e.getMessage());
        }
    }
}
