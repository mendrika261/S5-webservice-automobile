package mg.springboot.controller;

import jakarta.servlet.http.HttpServletRequest;
import mg.springboot.model.Message;
import mg.springboot.entity.Utilisateur;
import mg.springboot.exception.AccessDeniedException;
import mg.springboot.repository.MessageRepository;
import mg.springboot.security.Response;
import mg.springboot.security.Token;
import mg.springboot.service.DiscussionService;
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
    private final DiscussionService discussionService;

    public MessageController(MessageRepository messageRepository, TokenService tokenService, UtilisateurService utilisateurService, DiscussionService discussionService) {
        this.messageRepository = messageRepository;
        this.tokenService = tokenService;
        this.utilisateurService = utilisateurService;
        this.discussionService = discussionService;
    }

    @GetMapping("/api/messages")
    public Response<?> getDiscussions(HttpServletRequest request) {
        Token token = tokenService.getToken(request);
        Utilisateur utilisateur1 = token.getUtilisateur();
        return Response.send(HttpStatus.OK, "success",
                discussionService.get_discussions(utilisateur1));
    }

    @GetMapping("/api/messages/{id}")
    public Response<?> getDiscussions(HttpServletRequest request, @PathVariable String id) {
        Token token = tokenService.getToken(request);
        Utilisateur utilisateur1 = token.getUtilisateur();
        Utilisateur utilisateur2 = utilisateurService.findById(id);
        return Response.send(HttpStatus.OK, "success",
                discussionService.getDiscussionEntre(utilisateur1, utilisateur2));
    }


    @PostMapping("/api/messages")
    public Response<?> postMessage(HttpServletRequest request, String idReceveur, String message) {
        Token token = tokenService.getToken(request);
        Utilisateur utilisateur1 = token.getUtilisateur();
        Utilisateur utilisateur2 = utilisateurService.findById(idReceveur);
        if(message == null || message.isEmpty())
            throw new AccessDeniedException("Le message ne peut pas être vide");
        if(utilisateur1.getId().equals(utilisateur2.getId()))
            throw new AccessDeniedException("Vous ne pouvez pas vous envoyer des messages à vous-même");
        return Response.send(HttpStatus.OK, "success",
                discussionService.envoyerMessage(utilisateur1,utilisateur2,message));
    }
}
