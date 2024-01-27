package mg.springboot.repository;

import mg.springboot.entity.Annonce;
import mg.springboot.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AnnonceRepository extends JpaRepository<Annonce, Integer> {
    List<Annonce> findByEtatAndVoiture_UtilisateurNot(Integer etat, Utilisateur utilisateur);


    List<Annonce> findAllByEtat(int i);
}