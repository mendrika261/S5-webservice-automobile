package mg.springboot.service;

import lombok.Getter;
import lombok.Setter;
import mg.springboot.model.Discussion;
import mg.springboot.model.Message;
import mg.springboot.entity.Utilisateur;
import mg.springboot.repository.DiscussionRepository;
import mg.springboot.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@Getter
@Setter
public class DiscussionService {
    MessageRepository messageRepository;
    DiscussionRepository discussionRepository;

    public DiscussionService(MessageRepository messageRepository, DiscussionRepository discussionRepository) {
        this.messageRepository = messageRepository;
        this.discussionRepository = discussionRepository;
    }

    public List<Discussion> get_discussions(Utilisateur utilisateur)
    {
        return discussionRepository.findDiscussions(utilisateur.getId());
    }

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
        return discussionRepository.save(discussion);
    }
}
