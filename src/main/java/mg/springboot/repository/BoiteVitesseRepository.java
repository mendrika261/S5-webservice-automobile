package mg.springboot.repository;

import mg.springboot.entity.BoiteVitesse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoiteVitesseRepository extends JpaRepository<BoiteVitesse, Integer> {
}