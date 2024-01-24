package mg.springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "commission")
@NoArgsConstructor
public class Commission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "max_prix",nullable = false)
    @NotNull(message = "Le prix doit être renseignée")
    @Min(value = 0, message = "Le prix doit être supérieur à 0")
    private Double maxPrix;

    @Column(name = "min_prix",nullable = false)
    @NotNull(message = "Le prix doit être renseignée")
    @Min(value = 0, message = "Le prix doit être supérieur à 0")
    private Double minPrix;

    @Column(name = "pourcentage",nullable = false)
    @NotNull(message = "Le pourcentage doit être renseignée")
    @Min(value = 0, message = "Le pourcentage doit être supérieur à 0")
    private Double pourcentage;

    @Column(name = "date_application", nullable = false)
    private @NotNull(message = "La date d'application doit être renseignée")
    LocalDateTime dateApplication;

    public Commission(Double maxPrix, Double minPrix, Double pourcentage, LocalDateTime dateApplication) {
        this.maxPrix = maxPrix;
        this.minPrix = minPrix;
        this.pourcentage = pourcentage;
        this.dateApplication = dateApplication;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Commission &&
                maxPrix.equals(((Commission) obj).maxPrix)
                && minPrix.equals(((Commission) obj).minPrix)
                && pourcentage.equals(((Commission) obj).pourcentage);
    }

    @Override
    public String toString() {
        return "{" + maxPrix +
                ", " + minPrix +
                ", " + pourcentage + "%}";
    }
}