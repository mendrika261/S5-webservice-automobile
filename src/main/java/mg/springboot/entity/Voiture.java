package mg.springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "voiture", uniqueConstraints = {@UniqueConstraint(columnNames = {"immatriculation", "utilisateur_id"})})
public class Voiture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "immatriculation", nullable = false)
    @NotNull(message = "L'immatriculation doit être renseignée")
    @NotBlank(message = "L'immatriculation ne peut pas être vide")
    private String immatriculation;

    @Column(name = "premiere_main", nullable = false)
    @NotNull(message = "Vous devez préciser si la voiture est de première main")
    private Boolean premiereMain;

    @Column(name = "date_fin_assurance", nullable = false)
    @NotNull(message = "La date de fin d'assurance doit être renseignée")
    private LocalDate dateFinAssurance;

    @Column(name = "date_controle_tech", nullable = false)
    @NotNull(message = "La dernière date de contrôle technique doit être renseignée")
    private LocalDate dateControleTech;

    @Column(name = "kilometrage")
    @NotNull(message = "Le kilométrage de la voiture doit être renseigné")
    private Double kilometrage;

    @Column(name = "kilometrage_vidange")
    @NotNull(message = "Le kilométrage après le dernier vidange doit être renseigné")
    private Double kilometrageVidange;

    @Column(name = "mise_en_circulation")
    @NotNull(message = "La date de mise en circulation doit être renseignée")
    private int miseEnCirculation;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    @NotNull(message = "L'utilisateur doit être renseigné")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "etat_voiture_id",nullable = false)
    @NotNull(message = "L'état de la voiture doit être renseigné")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private EtatVoiture etatVoiture;


    @ManyToOne
    @JoinColumn(name = "sortie_voiture_id",nullable = false)
    @NotNull(message = "La sortie de la voiture doit être renseignée")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SortieVoiture sortieVoiture;

    @ManyToOne
    @JoinColumn(name = "couleur_id",nullable = false)
    @NotNull(message = "La couleur doit être renseignée")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Couleur couleur;
}