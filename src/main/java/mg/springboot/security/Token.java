package mg.springboot.security;

import jakarta.persistence.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.Utilisateur;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "token")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false)
    private UUID value = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    @Column(name = "duration", nullable = false)
    private int duration = 3600;

    @Column(name = "last_activity", nullable = false)
    private LocalDateTime lastActivity = LocalDateTime.now();

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    public Token() {
    }

    public Token(Utilisateur utilisateur) {
        setUtilisateur(utilisateur);
    }

    public boolean isValid() {
        return !isDeleted() && getLastActivity().plusSeconds(getDuration()).isAfter(LocalDateTime.now());
    }

    public static String getBearerToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}