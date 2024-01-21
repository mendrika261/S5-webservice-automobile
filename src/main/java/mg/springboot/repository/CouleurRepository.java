package mg.springboot.repository;

import mg.springboot.entity.Couleur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouleurRepository extends JpaRepository<Couleur, Integer> {}