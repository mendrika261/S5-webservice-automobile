package mg.springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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
    private Integer  etat;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "nom_avec_chemin")
    private String nomAvecChemin;

    @Column(name = "type")
    private String type;

}