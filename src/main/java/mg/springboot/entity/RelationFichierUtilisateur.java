package mg.springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "relation_fichier_utilisateur")
public class RelationFichierUtilisateur {
    @ManyToOne
    @JoinColumn(name = "utilisateur_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Utilisateur utilisateur;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "fichier_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Fichier fichier;

    @Column(name = "date")
    private LocalDate date;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

}