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
@Table(name = "option")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "designation", unique = true)
    @NotNull(message = "La designation est obligatoire")
    @NotBlank(message = "La designation ne peut pas être vide")
    private String designation;

    @Column(name = "type_valeur")
    @NotNull(message = "Le type est obligatoire")
    @NotBlank(message = "Le type ne peut pas être vide")
    private String typeValeur;

    @Column(name = "valeurs")
    @NotNull(message = "Il faut au moins une valeur")
    @NotBlank(message = "Il faut au moins une valeur")
    @Pattern(regexp = "^([A-Za-z0-9]+,)*[A-Za-z0-9]+$", message = "Les valeurs doivent être séparées par des virgules")
    private String valeurs;
}