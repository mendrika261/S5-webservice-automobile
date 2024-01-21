package mg.springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "couleur")
public class Couleur {
  @Column(name = "code_couleur") private String codeCouleur;

  @Column(name = "nom") private String nom;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Integer id;
}
