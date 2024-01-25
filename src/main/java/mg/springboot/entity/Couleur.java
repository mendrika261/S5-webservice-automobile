package mg.springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "couleur",uniqueConstraints ={
        @UniqueConstraint( columnNames = {"nom","code_couleur"})
})
public class Couleur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "code_couleur",nullable = false)
    @NotNull(message = "La couleur est obligatoire")
    @NotBlank(message = "La couleur ne peut pas être vide")
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "La couleur doit être au format hexadécimal")
    private String codeCouleur;

    @Column(name = "nom", nullable = false)
    @NotNull(message = "Le nom est obligatoire")
    @NotBlank(message = "Le nom ne peut pas être vide")
    private String nom;
}