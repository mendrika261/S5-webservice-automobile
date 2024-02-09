package mg.springboot.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mg.springboot.entity.Utilisateur;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Discussion {
    @Id
    private String id;
    private String utilisateur1;
    private String utilisateur2;
    private Message[] messages;
    @Transient
    private Utilisateur utilisateurObjet1;
    @Transient
    private Utilisateur utilisateurObjet2;
}
