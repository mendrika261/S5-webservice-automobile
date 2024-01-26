package mg.springboot.repository;

import mg.springboot.entity.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AnnonceRepository extends JpaRepository<Annonce, Integer> {
    @Query("select a from Annonce a where a.etat = ?1 ")
    List<Annonce> findByEtat(Integer etat);

    List<Annonce> findAllByEtat(int i);
}