package mg.springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pays")
public class Pays {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nom", nullable = false, unique = true)
    @NotNull(message = "Le nom est obligatoire")
    @NotBlank(message = "Le nom ne peut pas être vide")
    private String nom;

    @Column(name = "code", nullable = false, unique = true)
    @NotNull(message = "Le code est obligatoire")
    @NotBlank(message = "Le code ne peut pas être vide")
    @Size(min = 2, message = "Le code doit avoir au moins 2 caractères")
    private String code;
}