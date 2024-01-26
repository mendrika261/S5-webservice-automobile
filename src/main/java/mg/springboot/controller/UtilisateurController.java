package mg.springboot.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.Utilisateur;
import mg.springboot.exception.ValidationException;
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
        System.out.println("tato");
        System.out.println("email"+email+" mdp "+motDePasse );
        Optional<Utilisateur> utilisateur = utilisateurService.findByEmailAndMotDePasse(email, motDePasse);
        if (utilisateur.isPresent())
            return Response.send(HttpStatus.OK, "success","connexion reussie", tokenService.createFor(utilisateur.get()));
        return Response.send(HttpStatus.OK, "error", "Login ou mot de passe incorrect");
    }

    @DeleteMapping("/deconnexion")
    public Response<?> deconnexion(HttpServletRequest request) {
        Token token = tokenService.getToken(request);
        tokenService.delete(token);
        //Response.denied("");
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
    public Response<?> modifyUtilisateur(@PathVariable String id, @Valid Utilisateur utilisateur) {
        return Response.send(HttpStatus.OK, "success", "L'utilisateur a été modifié",
                    utilisateurService.modify(id, utilisateur));
    }

    @DeleteMapping("/utilisateurs/{id}")
    public Response<?> deleteUtilisateur(@PathVariable String id) {
        return Response.send(HttpStatus.OK, "success", "L'utilisateur a été supprimé",
                    utilisateurService.delete(id));
    }
}
