package mg.springboot.repository;

import mg.springboot.entity.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoitureRepository extends JpaRepository<Voiture, Integer> {}