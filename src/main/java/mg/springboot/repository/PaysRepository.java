package mg.springboot.repository;

import mg.springboot.entity.Pays;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaysRepository extends JpaRepository<Pays, Integer> {}