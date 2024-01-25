package mg.springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "boite_vitesse",uniqueConstraints ={
        @UniqueConstraint( columnNames = {"nom"})
})
public class BoiteVitesse {

    @Column(name = "nom")
    @NotNull(message = "Le nom est obligatoire")
    @NotBlank(message = "Le nom ne peut pas être vide")
    private String nom;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

}