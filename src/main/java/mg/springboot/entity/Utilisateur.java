package mg.springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "utilisateur")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "nom", nullable = false)
    @NotNull(message = "Le nom est obligatoire")
    @NotBlank(message = "Le nom ne peut pas être vide")
    private String nom;

    @Column(name = "prenom", nullable = false)
    @NotNull(message = "Le prénom est obligatoire")
    @NotBlank(message = "Le prénom ne peut pas être vide")
    private String prenom;

    @Column(name = "contact")
    @NotNull(message = "Le contact est obligatoire")
    @NotBlank(message = "Le contact ne peut pas être vide")
    @Pattern(regexp = "^03[0-9] [0-9]{2} [0-9]{3} [0-9]{2}$", message = "Le contact doit être au format 03xx xx xxx xx")
    private String contact;

    @Column(name = "email", nullable = false, unique = true)
    @NotNull(message = "L'email est obligatoire")
    @NotBlank(message = "L'email ne peut pas être vide")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]{2,}\\.[a-z]{2,4}$", message = "L'email est invalide")
    private String email;

    @Column(name = "mot_de_passe", nullable = false)
    @NotNull(message = "Le mot de passe est obligatoire")
    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String motDePasse;

    @Column(name = "level")
    @NotNull(message = "Le level est obligatoire")
    @Min(value=0, message = "Le level doit être supérieur ou égal à 0")
    private int level;
}