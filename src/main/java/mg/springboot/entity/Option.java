package mg.springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "option")
public class Option {
  @Column(name = "designation") private String designation;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "type_valeur") private String typeValeur;

  @Column(name = "valeur") private String valeur;

  // valeur? typeValeur?
}