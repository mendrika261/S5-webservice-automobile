package mg.springboot.repository;

import mg.springboot.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Integer> {}