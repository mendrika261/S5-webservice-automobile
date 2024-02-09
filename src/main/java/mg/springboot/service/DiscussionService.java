package mg.springboot.service;

import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import mg.springboot.model.Discussion;
import mg.springboot.model.Message;
import mg.springboot.entity.Utilisateur;
import mg.springboot.repository.DiscussionRepository;
import mg.springboot.repository.MessageRepository;
import mg.springboot.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Getter
@Setter
public class DiscussionService {
    MessageRepository messageRepository;
    DiscussionRepository discussionRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final NotificationService notificationService;

    public DiscussionService(MessageRepository messageRepository, DiscussionRepository discussionRepository,
                             UtilisateurRepository utilisateurRepository, NotificationService notificationService) {
        this.messageRepository = messageRepository;
        this.discussionRepository = discussionRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.notificationService = notificationService;
    }

    public List<Discussion> get_discussions(Utilisateur utilisateur)
    {
        List<Discussion> discussions = discussionRepository.findDiscussions(utilisateur.getId());
        for (Discussion discussion: discussions) {
            discussion.setUtilisateurObjet1(utilisateurRepository.findById(discussion.getUtilisateur1()).get());
            discussion.setUtilisateurObjet2(utilisateurRepository.findById(discussion.getUtilisateur2()).get());
            discussion.setMessages(new Message[]{discussion.getMessages()[discussion.getMessages().length - 1]});
        }
        return discussions;
    }

    public Discussion getDiscussionEntre(Utilisateur utilisateur1, Utilisateur utilisateur2)
    {
        Discussion discussion = discussionRepository.findDiscussionsByTwoUsers(utilisateur1.getId(), utilisateur2.getId());
        discussion.setUtilisateurObjet1(utilisateurRepository.findById(discussion.getUtilisateur1()).get());
        discussion.setUtilisateurObjet2(utilisateurRepository.findById(discussion.getUtilisateur2()).get());
        return discussion;
    }

    @Transactional
    public Discussion  envoyerMessage(Utilisateur sender,Utilisateur receiver,String message)
    {
        LocalDateTime localDateTime=LocalDateTime.now();
        Message message1=new Message();
        message1.setMessage(message);
        message1.setSender(sender.getId());
        message1.setDateheure(localDateTime);
        Discussion discussion=discussionRepository.findDiscussionsByTwoUsers(sender.getId(),receiver.getId());
        if(discussion==null)
        {
            discussion=new Discussion();
            discussion.setId(UUID.randomUUID().toString());
            discussion.setUtilisateur1(sender.getId());
            discussion.setUtilisateur2(receiver.getId());
            discussion.setMessages(new Message[1]);
            discussion.getMessages()[0]=message1;

            return discussionRepository.save(discussion);
        }
        Message[] l1= discussion.getMessages();
        int n=l1.length;
        Message[] l2= Arrays.copyOf(l1,n+1);
        l2[n]=message1;
        discussion.setMessages(l2);
        notificationService.sendNotificationTo(sender.getNomComplet(), message, null, receiver.getId());
        return discussionRepository.save(discussion);
    }
}
