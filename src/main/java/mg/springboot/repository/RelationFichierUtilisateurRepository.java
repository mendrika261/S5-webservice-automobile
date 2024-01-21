package mg.springboot.repository;

import mg.springboot.entity.RelationFichierUtilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationFichierUtilisateurRepository
    extends JpaRepository<RelationFichierUtilisateur, Integer> {}