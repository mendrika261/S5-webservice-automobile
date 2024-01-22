package mg.springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "option",uniqueConstraints ={
        @UniqueConstraint( columnNames = {"designation","type_valeur","valeur"})
})
public class Option {
    @Column(name = "designation")
    @NotNull(message = "La designation est obligatoire")
    @NotBlank(message = "La designation ne peut pas être vide")
    private String designation;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "type_valeur")
    @NotNull(message = "Le type  est obligatoire")
    @NotBlank(message = "Le type ne peut pas être vide")
    private String typeValeur;

    @Column(name = "valeur")
    @NotNull(message = "La valeur est obligatoire")
    @NotBlank(message = "La valeur ne peut pas être vide")
    private String valeur;

//valeur? typeValeur?

}