package mg.springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "etat_voiture")
public class EtatVoiture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;


    @Column(name = "designation", unique = true)
    @NotNull(message = "La designation doit être renseignée")
    @NotBlank(message = "La designation ne doit pas être vide")
    private String designation;
}