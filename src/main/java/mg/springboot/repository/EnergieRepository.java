package mg.springboot.repository;

import mg.springboot.entity.Energie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnergieRepository extends JpaRepository<Energie, Integer> {
}