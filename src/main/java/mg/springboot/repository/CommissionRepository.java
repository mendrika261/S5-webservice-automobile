package mg.springboot.repository;

import mg.springboot.entity.Commission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CommissionRepository extends JpaRepository<Commission, Integer> {
    List<Commission> findAllByOrderByDateApplicationDesc();

    List<Commission> findAllByDateApplicationBeforeOrderByDateApplicationAsc(LocalDateTime dateTimeActuelle);
}