package mg.springboot.controller;

import jakarta.servlet.http.HttpServletRequest;
import mg.springboot.entity.Discussion;
import mg.springboot.entity.Utilisateur;
import mg.springboot.exception.AccessDeniedException;
import mg.springboot.repository.MessageRepository;
import mg.springboot.security.Response;
import mg.springboot.security.Token;
import mg.springboot.service.TokenService;
import mg.springboot.service.UtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class MessageController {
    private final MessageRepository messageRepository;
    private final TokenService tokenService;
    private final UtilisateurService utilisateurService;

    public MessageController(MessageRepository messageRepository, TokenService tokenService, UtilisateurService utilisateurService) {
        this.messageRepository = messageRepository;
        this.tokenService = tokenService;
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/api/messages/utilisateurs/{id}")
    public Response<?> getMessages(HttpServletRequest request, @PathVariable("id") String idUtilisateur2) {
        Token token = tokenService.getToken(request);
        Utilisateur utilisateur1 = token.getUtilisateur();
        Utilisateur utilisateur2 = utilisateurService.findById(idUtilisateur2);
        if(utilisateur1.getId().equals(utilisateur2.getId()))
            throw new AccessDeniedException("Vous ne pouvez pas vous envoyer des messages à vous-même");
        return Response.send(HttpStatus.OK, "success",
                messageRepository.searchDiscussionByEnvoyeurIdOrReceveurIdOrderByDateheureDesc(utilisateur1.getId(), utilisateur2.getId()));
    }

    @PostMapping("/api/messages/utilisateurs/{id}")
    public Response<?> postMessage(HttpServletRequest request, @PathVariable("id") String idUtilisateur2, String message) {
        Token token = tokenService.getToken(request);
        Utilisateur utilisateur1 = token.getUtilisateur();
        Utilisateur utilisateur2 = utilisateurService.findById(idUtilisateur2);
        if(utilisateur1.getId().equals(utilisateur2.getId()))
            throw new AccessDeniedException("Vous ne pouvez pas vous envoyer des messages à vous-même");
        return Response.send(HttpStatus.OK, "success",
                messageRepository.save(new Discussion(null, utilisateur1.getId(), utilisateur2.getId(), message, LocalDateTime.now())));
    }
}
