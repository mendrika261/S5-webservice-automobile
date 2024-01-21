package mg.springboot.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "voiture")
public class Voiture {
  @Column(name = "immatriculation") private String immatriculation;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "premiere_main") private Boolean premiereMain;

  @Column(name = "date_fin_assurance") private LocalDate dateFinAssurance;

  @Column(name = "date_controle_tech") private LocalDate dateControleTech;

  @Column(name = "kilometrage") private Double kilometrage;

  @Column(name = "description") private String description;

  @ManyToOne
  @JoinColumn(name = "utilisateur_id")
  private Utilisateur utilisateur;

  @ManyToOne
  @JoinColumn(name = "etat_voiture_id", nullable = false)
  private EtatVoiture etatVoiture;

  @ManyToOne
  @JoinColumn(name = "sortie_voiture_id", nullable = false)
  private SortieVoiture sortieVoiture;

  @Column(name = "kilometrage_vidange") private Double kilometrageVidange;

  @Column(name = "mise_en_circulation") private LocalDate miseEnCirculation;
}