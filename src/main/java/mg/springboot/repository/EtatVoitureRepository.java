package mg.springboot.repository;

import mg.springboot.entity.EtatVoiture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtatVoitureRepository extends JpaRepository<EtatVoiture, Integer> {
}