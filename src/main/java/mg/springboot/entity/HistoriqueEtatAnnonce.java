package mg.springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "historique_etat_annonce")
public class HistoriqueEtatAnnonce {
    @ManyToOne
    @JoinColumn(name = "annonce_id")
    @NotNull(message = "Annonce obligatoire")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Annonce annonce;

    @Column(name = "data_action")
    @NotNull(message = "Date obligatoire")
    private LocalDateTime data_action;

    @ManyToOne
    @JoinColumn(name = "utilisateur_origine_id")
    private Utilisateur utilisateur_origine;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "etat")
    @NotNull(message = "etat obligatoire")
    private Integer etat;

}