package mg.springboot.repository;

import mg.springboot.entity.Fichier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FichierRepository extends JpaRepository<Fichier, Integer> {}
