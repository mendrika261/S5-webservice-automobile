package mg.springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "marque")
public class Marque {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "nom", nullable = false, unique = true)
  @NotNull(message = "Le nom est obligatoire")
  @NotBlank(message = "Le nom ne peut pas être vide")
  private String nom;
}
