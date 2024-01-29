package mg.springboot.repository;

import mg.springboot.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface AnnonceRepository extends JpaRepository<Annonce, Integer> {
    List<Annonce> findByEtatAndVoiture_UtilisateurNot(Integer etat, Utilisateur utilisateur);

    @Query("""
            select a from Annonce a
            where a.voiture.etatVoiture in ?1 and a.voiture.couleur in ?2 and a.voiture.sortieVoiture.modele.marque in ?3""")
    List<Annonce> findByVoiture_EtatVoitureInAndVoiture_CouleurInAndVoiture_SortieVoitureIn(Collection<EtatVoiture> etatVoitures, Collection<Couleur> couleurs, Collection<Marque> marques);

    @Query("select a from Annonce a where a.voiture.etatVoiture = ?1")
    List<Annonce> findByVoiture_EtatVoiture(EtatVoiture etatVoiture);

    @Query("select a from Annonce a where a.voiture.etatVoiture in ?1")
    List<Annonce> findByVoiture_EtatVoitureIn(Collection<EtatVoiture> etatVoitures);

    @Query("select a from Annonce a where a.voiture.etatVoiture in ?1 and a.voiture.couleur in ?2")
    List<Annonce> findByVoiture_EtatVoitureInAndVoiture_CouleurIn(Collection<EtatVoiture> etatVoitures, Collection<Couleur> couleurs);


    List<Annonce> findAllByEtat(int i);
}