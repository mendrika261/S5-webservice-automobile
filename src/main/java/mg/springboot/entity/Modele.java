package mg.springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.bind.annotation.DeleteMapping;

@Getter
@Setter
@Entity
@Table(name = "modele",uniqueConstraints ={
        @UniqueConstraint( columnNames = {"nom","marque_id"})
})
public class Modele {
    @Column(name = "nom")
    @NotNull(message = "Le nom est obligatoire")
    @NotBlank(message = "Le nom ne peut pas être vide")
    private String nom;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "marque_id")

    @NotNull(message = "La marque est obligatoire")
    @OnDelete(action=OnDeleteAction.CASCADE)
    private Marque marque;

}