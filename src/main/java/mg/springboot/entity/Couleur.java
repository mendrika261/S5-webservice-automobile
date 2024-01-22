package mg.springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "couleur",uniqueConstraints ={
        @UniqueConstraint( columnNames = {"nom","code_couleur"})
})
public class Couleur {
    @Column(name = "code_couleur")
    @NotNull(message = "La couleur est obligatoire")
    @NotBlank(message = "La couleur ne peut pas être vide")
    private String codeCouleur;

    @Column(name = "nom")
    @NotNull(message = "Le nom est obligatoire")
    @NotBlank(message = "Le nom ne peut pas être vide")
    private String nom;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

}