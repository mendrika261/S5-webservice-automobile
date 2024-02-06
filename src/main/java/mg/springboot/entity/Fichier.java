package mg.springboot.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "fichier")
public class Fichier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "etat")
    private Integer  etat = 0;

    @Column(name = "nom_avec_chemin")
    private String lien;

    @Column(name = "type")
    private String type;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "voiture_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Voiture voiture;

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Fichier && ((Fichier) obj).getId().equals(this.id);
    }
}