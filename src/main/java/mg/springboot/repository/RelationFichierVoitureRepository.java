package mg.springboot.repository;

import mg.springboot.entity.RelationFichierVoiture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationFichierVoitureRepository extends JpaRepository<RelationFichierVoiture, Long> {
}