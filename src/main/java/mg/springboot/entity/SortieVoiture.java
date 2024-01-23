package mg.springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sortie_voiture",
    uniqueConstraints = {
                @UniqueConstraint(name = "uk_sortie_voiture_modele_couleur_energie_boite_vitesse_pays",
                        columnNames = {"vitesse_max","annee","nbr_place","nbr_porte","consommation","modele_id",
                                "energie_id","boite_vitesse_id","pays_id","puissance"})
        })
public class SortieVoiture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "vitesse_max", nullable = false)
    @NotNull(message = "La vitesse max doit être renseignée")
    private Double vitesseMax;

    @Column(name = "annee", nullable = false)
    @NotNull(message = "L'année doit être renseignée")
    @Min(value = 1900, message = "L'année doit être supérieur à 1900")
    @Max(value = 2030, message = "L'année doit être inférieur à 2030")
    private Integer annee;

    @Column(name = "nbr_place", nullable = false)
    @NotNull(message = "Le nombre de place doit être renseignée")
    @Min(value = 1, message = "Le nombre de place doit être supérieur à 1")
    @Max(value = 100, message = "Le nombre de place doit être inférieur à 100")
    private Integer nbrPlace;

    @Column(name = "nbr_porte", nullable = false)
    @NotNull(message = "Le nombre de porte doit être renseignée")
    @Min(value = 1, message = "Le nombre de porte doit être supérieur à 1")
    private Integer nbrPorte;

    @Column(name = "consommation", nullable = false)
    @NotNull(message = "La consommation doit être renseignée")
    @Min(value = 0, message = "La consommation doit être supérieur à 0")
    private Double consommation;

    @Column(name = "puissance")
    @NotNull(message = "La puissance doit être renseignée")
    @Min(value = 0, message = "La puissance doit être supérieur à 0")
    private Double puissance;

    @ManyToOne
    @JoinColumn(name = "modele_id", nullable = false)
    @NotNull(message = "Le modèle doit être renseignée")
    private Modele modele;

    @ManyToOne
    @JoinColumn(name = "energie_id",nullable = false)
    @NotNull(message = "L'énergie doit être renseignée")
    private Energie energie;

    @ManyToOne
    @JoinColumn(name = "boite_vitesse_id",nullable = false)
    @NotNull(message = "La boite de vitesse doit être renseignée")
    private BoiteVitesse boiteVitesse;

    @ManyToOne
    @JoinColumn(name = "pays_id",nullable = false)
    @NotNull(message = "Le pays doit être renseignée")
    private Pays pays;

}