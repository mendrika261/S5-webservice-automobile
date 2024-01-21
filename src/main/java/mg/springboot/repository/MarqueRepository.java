package mg.springboot.repository;

import mg.springboot.entity.Marque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarqueRepository extends JpaRepository<Marque, Integer> {
}