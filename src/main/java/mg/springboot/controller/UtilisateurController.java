package mg.springboot.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.Annonce;
import mg.springboot.entity.Utilisateur;
import mg.springboot.entity.Voiture;
import mg.springboot.exception.AccessDeniedException;
import mg.springboot.repository.VoitureRepository;
import mg.springboot.security.Response;
import mg.springboot.security.Token;
import mg.springboot.service.AnnonceService;
import mg.springboot.service.TokenService;
import mg.springboot.service.UtilisateurService;
import mg.springboot.service.VoitureService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Getter
@Setter
@RestController
public class UtilisateurController {
    private final UtilisateurService utilisateurService;
    private final TokenService tokenService;
    private final VoitureRepository voitureRepository;
    private final VoitureService voitureService;
    private final AnnonceService annonceService;

    public UtilisateurController(UtilisateurService utilisateurRepository,
                                 TokenService tokenRepository,
                                 VoitureRepository voitureRepository,
                                 VoitureService voitureService,
                                 AnnonceService annonceService) {
        this.utilisateurService = utilisateurRepository;
        this.tokenService = tokenRepository;
        this.voitureRepository = voitureRepository;
        this.voitureService = voitureService;
        this.annonceService = annonceService;
    }

    @PostMapping("/connexion")
    public Response<?> connexion(String email, String motDePasse) {
        Optional<Utilisateur> utilisateur = utilisateurService.findByEmailAndMotDePasse(email, motDePasse);
        if (utilisateur.isPresent()) {
            Utilisateur utilisateurR = utilisateur.get();
            if (utilisateurR.getLevel() != Utilisateur.LEVEL_ADMIN)
                return Response.send(HttpStatus.OK, "error", "Vous n'êtes pas administrateur");
            return Response.send(HttpStatus.OK, "success","Connexion réussie", tokenService.createFor(utilisateurR));
        }
        return Response.send(HttpStatus.OK, "error", "Login ou mot de passe incorrect");
    }

    @PostMapping("/tokens/utilisateur")
    public Response<?> connexionUtilisateur(HttpServletRequest request, String email, String motDePasse) {
        Token token = tokenService.getToken(request);
        if (token != null)
            throw new AccessDeniedException("Vous êtes déjà connecté");
        Optional<Utilisateur> utilisateur = utilisateurService.findByEmailAndMotDePasse(email, motDePasse);
        if (utilisateur.isPresent()) {
            Utilisateur utilisateurR = utilisateur.get();
            if (utilisateurR.getLevel() != Utilisateur.LEVEL_USER)
                return Response.send(HttpStatus.OK, "error", "Vous n'êtes pas utilisateur! Allez sur la page d'administration pour vous connecter");
            return Response.send(HttpStatus.OK, "success","Connexion réussie", tokenService.createFor(utilisateurR));
        }
        return Response.send(HttpStatus.OK, "error", "Login ou mot de passe incorrect");
    }

    @PostMapping("/auth/utilisateurs")
    public Response<?> inscription(HttpServletRequest httpServletRequest, @Valid Utilisateur utilisateur) {
        Token token = tokenService.getToken(httpServletRequest);
        if (token != null)
            throw new AccessDeniedException("Vous êtes déjà connecté");
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

    @DeleteMapping("/tokens/utilisateur")
    public Response<?> deconnexionUtilisateur(HttpServletRequest request) {
        Token token = tokenService.getToken(request);
        if (token == null)
            throw new AccessDeniedException("Vous n'êtes pas connecté");
        tokenService.delete(token);
        return Response.send(HttpStatus.OK, "success", "Déconnexion réussie", token);
    }

    @GetMapping("/admin/utilisateurs")
    public Response<?> getUtilisateurs() {
        return Response.send(HttpStatus.OK, "success", utilisateurService.findAll());
    }

    @GetMapping("/admin/utilisateurs/{id}")
    public Response<?> getUtilisateurs(@PathVariable String id) {
        return Response.send(HttpStatus.OK, "success", utilisateurService.findById(id));
    }

    @PostMapping("/admin/utilisateurs")
    public Response<?> addUtilisateur(@Valid Utilisateur utilisateur) {
        return Response.send(HttpStatus.OK, "success", "L'utilisateur a été ajouté",
                    utilisateurService.save(utilisateur));
    }

    @PutMapping("/admin/utilisateurs/{id}")
    public Response<?> modifyUtilisateur(@PathVariable String id, Utilisateur utilisateur) {
        return Response.send(HttpStatus.OK, "success", "L'utilisateur a été modifié",
                    utilisateurService.modify(id, utilisateur));
    }

    @DeleteMapping("/admin/utilisateurs/{id}")
    public Response<?> deleteUtilisateur(@PathVariable String id) {
        return Response.send(HttpStatus.OK, "success", "L'utilisateur a été supprimé",
                    utilisateurService.delete(id));
    }

    @DeleteMapping("/admin/utilisateurs/{id}/photo/{photoId}")
    public Response<?> deletePhoto(@PathVariable String id, @PathVariable Integer photoId) {
        return Response.send(HttpStatus.OK, "success", "La photo a été supprimée",
                    utilisateurService.deletePhoto(id, photoId));
    }

    @PutMapping("/admin/utilisateurs/{id}/photo/{photoId}")
    public Response<?> modifyPhoto(@PathVariable String id, @PathVariable Integer photoId) {
        return Response.send(HttpStatus.OK, "success", "La photo a été modifiée",
                    utilisateurService.modifyPhoto(id, photoId));
    }

    @PutMapping("/admin/utilisateurs/{id}/mot-de-passe")
    public Response<?> modifyMotDePasse(@PathVariable String id, String motDePasse, String nouveauMotDePasse) {
        return Response.send(HttpStatus.OK, "success", "Le mot de passe a été modifié",
                    utilisateurService.modifyMotDePasse(id, motDePasse, nouveauMotDePasse));
    }

    @GetMapping("/api/voitures")
    public Response<?> getVoitures(HttpServletRequest httpServletRequest) {
        Token token = tokenService.getToken(httpServletRequest);
        if (token == null)
            throw new AccessDeniedException("Vous n'êtes pas connecté");
        return Response.send(HttpStatus.OK, "success", utilisateurService.getVoitures(token.getUtilisateur().getId()));
    }

    @PostMapping("/api/voitures")
    public Response<?> addVoiture(HttpServletRequest httpServletRequest, @Valid Voiture voiture) {
        Token token = tokenService.getToken(httpServletRequest);
        if (token.getUtilisateur().getId().equals(voiture.getUtilisateur().getId()))
            return Response.send(HttpStatus.OK, "success", "La voiture a été ajoutée",
                        voitureRepository.save(voiture));
        throw new AccessDeniedException("Vous n'avez pas le droit d'ajouter cette voiture à cet utilisateur");
    }

    @PutMapping("/api/voitures/{id}")
    public Response<?> modifyVoiture(HttpServletRequest httpServletRequest, @PathVariable Integer id, Voiture voiture) {
        Token token = tokenService.getToken(httpServletRequest);
        Voiture voitureR = voitureRepository.findById(id).get();
        if (token.getUtilisateur().getId().equals(voitureR.getUtilisateur().getId()))
            return Response.send(HttpStatus.OK, "success", "La voiture a été modifiée",
                        voitureService.modify(id, voiture));
        throw new AccessDeniedException("Vous n'avez pas le droit de modifier cette voiture");
    }

    @DeleteMapping("/api/voitures/{id}")
    public Response<?> deleteVoiture(HttpServletRequest httpServletRequest, @PathVariable Integer id) {
        Token token = tokenService.getToken(httpServletRequest);
        Voiture voitureR = voitureRepository.findById(id).get();
        if (token.getUtilisateur().getId().equals(voitureR.getUtilisateur().getId()))
            return Response.send(HttpStatus.OK, "success", "La voiture a été supprimée",
                        voitureService.delete(id));
        throw new AccessDeniedException("Vous n'avez pas le droit de supprimer cette voiture");
    }

    @PostMapping("/api/annonces")
    public Response<?> addAnnonce(HttpServletRequest httpServletRequest, @Valid Annonce annonce) {
        Token token = tokenService.getToken(httpServletRequest);
        if (token.getUtilisateur().getId().equals(annonce.getVoiture().getUtilisateur().getId()))
            return Response.send(HttpStatus.OK, "success", "L'annonce a été ajoutée",
                        annonceService.save(annonce));
        throw new AccessDeniedException("Vous n'avez pas le droit d'ajouter cette annonce à cet utilisateur");
    }

}
