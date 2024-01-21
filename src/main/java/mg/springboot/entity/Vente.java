package mg.springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "vente")
public class Vente {
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "annonce_id",nullable = false)
    private Annonce annonce;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "paiement_id")
    private Paiement paiement;

    @ManyToOne
    @JoinColumn(name = "commission_id")
    private Commission commission;

    @Column(name = "remise")
    private Double remise;

}