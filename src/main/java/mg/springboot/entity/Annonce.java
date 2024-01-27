package mg.springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "annonce")
public class Annonce {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "etat", nullable = false)
    @NotNull(message = "L'état de l'annonce doit être renseigné")
    private Integer etat = ETAT_NON_VALIDE;

    @Column(name = "date_fin")
    private LocalDateTime dateFin;

    @Column(name = "date_validation")
    private LocalDateTime dateValidation;

    @Column(name = "date", nullable = false)
    @NotNull(message = "La date de création de l'annonce doit être renseignée")
    private LocalDateTime date;

    @Column(name = "prix", nullable = false)
    @NotNull(message = "Le prix de l'annonce doit être renseigné")
    private Double prix;

    @ManyToOne
    @JoinColumn(name = "validateur_id")
    private Utilisateur validateur;

    @ManyToOne
    @JoinColumn(name = "voiture_id", nullable = false, unique = true)
    @NotNull(message = "La voiture de l'annonce doit être renseignée")
    private Voiture voiture;

    @Transient
    private Commission commission;
    @Transient
    private double valeurCommission;
    @Transient
    private String etatLibelle;

    @Transient
    public static final int ETAT_NON_VALIDE = 0;
    @Transient
    public static final int ETAT_VALIDE = 10;
    @Transient
    public static final int ETAT_EXPIRE = 20;
    @Transient
    public static final int ETAT_REFUSE = -10;

    @Transient
    public static final int ETAT_VENDUE = 30;



    public double getValeurCommission() {
        if(commission == null)
            return 0;
        return commission.getPourcentage() * prix / 100;
    }

    public String getEtatLibelle() {
        return switch (etat) {
            case ETAT_NON_VALIDE -> "En attente de validation";
            case ETAT_VALIDE -> "Validée";
            case ETAT_EXPIRE -> "Expirée";
            case ETAT_REFUSE -> "Refusée";
            case ETAT_VENDUE -> "Vendue";
            default -> "Inconnu";
        };
    }
}