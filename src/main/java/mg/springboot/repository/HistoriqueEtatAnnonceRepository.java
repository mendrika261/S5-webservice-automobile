package mg.springboot.repository;

import mg.springboot.entity.Annonce;
import mg.springboot.entity.HistoriqueEtatAnnonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoriqueEtatAnnonceRepository extends JpaRepository<HistoriqueEtatAnnonce, Integer> {
    @Query("select h from HistoriqueEtatAnnonce h where h.annonce = ?1")
    List<HistoriqueEtatAnnonce> findByAnnonce(Annonce annonce);
}