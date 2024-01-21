package mg.springboot.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "favori")
public class Favori {
  @Column(name = "etat", nullable = false) private Integer etat;

  @ManyToOne
  @JoinColumn(name = "annonce_id", nullable = false)
  private Annonce annonce;

  @ManyToOne
  @JoinColumn(name = "utilisateur_id", nullable = false)
  private Utilisateur utilisateur;

  @Column(name = "date_action", nullable = false) private LocalDate dateAction;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Integer id;
}
