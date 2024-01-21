package mg.springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "relation_fichier_utilisateur")
public class RelationFichierUtilisateur {
    @ManyToOne
    @JoinColumn(name = "utilisateur_id",nullable = false)
    private Utilisateur utilisateur;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "fichier_id",nullable = false)
    private Fichier fichier;

    @Column(name = "date")
    private LocalDate date;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

}