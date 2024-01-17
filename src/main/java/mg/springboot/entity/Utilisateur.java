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

    public void setEmail(String email) {
        if (email == null || email.isEmpty())
            throw new ValidationException("L'email ne peut pas être vide");
        this.email = email;
    }

    public void setMotDePasse(String motDePasse) {
        if (motDePasse == null || motDePasse.isEmpty())
            throw new ValidationException("Le mot de passe ne peut pas être vide");
        this.motDePasse = motDePasse;
    }

    public void setContact(String contact) {
        if (contact == null || contact.isEmpty())
            throw new ValidationException("Le contact ne peut pas être vide");
        this.contact = contact;
    }

    public void setNom(String nom) {
        if (nom == null || nom.isEmpty())
            throw new ValidationException("Le nom ne peut pas être vide");
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        if (prenom == null || prenom.isEmpty())
            throw new ValidationException("Le prénom ne peut pas être vide");
        this.prenom = prenom;
    }

    public void setLevel(int level) {
        if (level < 0)
            throw new ValidationException("Le niveau ne peut pas être négatif");
        this.level = level;
    }

    public void setId(String id) {
        if (id == null || id.isEmpty())
            throw new ValidationException("L'id ne peut pas être vide");
        this.id = id;
    }

    public void validate() {
        setEmail(email);
        setMotDePasse(motDePasse);
        setContact(contact);
        setNom(nom);
        setPrenom(prenom);
        setLevel(level);
    }
}