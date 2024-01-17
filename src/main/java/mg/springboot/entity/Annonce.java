package mg.springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "annonce")
public class Annonce {
    @ManyToOne
    @JoinColumn(name = "validateur_id")
    private Utilisateur validateur;

    @ManyToOne
    @JoinColumn(name = "voiture_id",nullable = false)
    private Voiture voiture;

    @Column(name = "etat")
    private Integer etat;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Column(name = "date_validation")
    private LocalDate dateValidation;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "prix")
    private Double prix;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

}