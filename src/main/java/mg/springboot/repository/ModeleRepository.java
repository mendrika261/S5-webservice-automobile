package mg.springboot.repository;

import mg.springboot.entity.Modele;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModeleRepository extends JpaRepository<Modele, Integer> {
}