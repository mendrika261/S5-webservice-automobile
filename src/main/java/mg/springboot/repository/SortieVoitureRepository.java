package mg.springboot.repository;

import mg.springboot.entity.SortieVoiture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SortieVoitureRepository
    extends JpaRepository<SortieVoiture, Integer> {}