package mg.springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mg.springboot.exception.ValidationException;

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
    private String nom;

    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "contact")
    private String contact;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;

    @Column(name = "level")
    private int level;

    public String getNom() {
        if(nom == null)
            throw new ValidationException("Le nom est obligatoire");
        return nom;
    }
}