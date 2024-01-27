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
@Table(name = "favori")
public class Favori {
    @Column(name = "etat",nullable = false)
    @NotNull(message = "Etat obligatoire")
    private Integer etat;

    @ManyToOne
    @NotNull(message = "annonce obligatoire")
    @JoinColumn(name = "annonce_id",nullable = false)
    private Annonce annonce;

    @ManyToOne
    @NotNull(message = "utilisateur obligatoire")
    @JoinColumn(name = "utilisateur_id",nullable = false)
    private Utilisateur utilisateur;

    @Column(name = "date_action")
    @NotNull(message = "date obligatoire")
    private LocalDateTime date_action;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Transient
    public static final int ETAT_NON_VALIDE = 0;
    @Transient
    public static final int ETAT_VALIDE = 10;

}