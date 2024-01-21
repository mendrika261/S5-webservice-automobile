package mg.springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sortie_voiture")
public class SortieVoiture {
  @Column(name = "vitesse_max") private Double vitesseMax;

  @Column(name = "annee") private Integer annee;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "nbr_place") private Integer nbrPlace;

  @Column(name = "nbr_porte") private Integer nbrPorte;

  @Column(name = "consommation") private Double consommation;

  @ManyToOne
  @JoinColumn(name = "modele_id", nullable = false)
  private Modele modele;

  @ManyToOne
  @JoinColumn(name = "couleur_id", nullable = false)
  private Couleur couleur;

  @ManyToOne
  @JoinColumn(name = "energie_id", nullable = false)
  private Energie energie;

  @ManyToOne
  @JoinColumn(name = "boite_vitesse_id", nullable = false)
  private BoiteVitesse boiteVitesse;

  @ManyToOne @JoinColumn(name = "pays_id", nullable = false) private Pays pays;

  @Column(name = "puissance") private Double puissance;
}
