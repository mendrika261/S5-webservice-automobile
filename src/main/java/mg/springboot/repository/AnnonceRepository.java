package mg.springboot.repository;

import mg.springboot.entity.Annonce;
import mg.springboot.entity.Utilisateur;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AnnonceRepository extends JpaRepository<Annonce, Integer>, PagingAndSortingRepository<Annonce, Integer> {
    List<Annonce> findByEtatAndVoiture_UtilisateurNot(Integer etat, Utilisateur utilisateur);

    List<Annonce> findAllByEtat(int i, PageRequest pageable);

    List<Annonce> findAllByEtatAndVoitureUtilisateurId(int etat, String idUtilisateur);

    List<Annonce> findAllByVoitureUtilisateurId(String idUtilisateur);
}