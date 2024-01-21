package mg.springboot.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "commission")
public class Commission {
  @Column(name = "max_prix", nullable = false) private Double maxPrix;

  @Column(name = "min_prix", nullable = false) private Double minPrix;

  @Column(name = "pourcentage", nullable = false) private Double pourcentage;

  @Column(name = "date_application", nullable = false)
  private LocalDate dateApplication;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Integer id;
}
