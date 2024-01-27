package mg.springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "vente")
public class Vente {
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "annonce_id",nullable = false,unique = true)
    @NotNull(message = "Annonce obligatoire")
    private Annonce annonce;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "paiement_id")
    @NotNull(message = "Le mode de paiement doit etre renseignee ")
    private Paiement paiement;

    @ManyToOne
    @JoinColumn(name = "commission_id")
    @NotNull(message = "La commission doit etre renseignee ")
    private Commission commission;

    @Column(name = "remise")
    private Double remise;

    @ManyToOne
    @JoinColumn(name = "utilisateur_acheteur_id")
    @NotNull(message = "L'acheteur doit etre renseignee ")
    private Utilisateur utilisateurAcheteur;

    @Column(name = "prix_vente")
    private Double prixVente;

}