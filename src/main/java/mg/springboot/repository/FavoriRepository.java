package mg.springboot.repository;

import mg.springboot.entity.Favori;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriRepository extends JpaRepository<Favori, Integer> {}