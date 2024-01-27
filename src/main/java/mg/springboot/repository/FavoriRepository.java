package mg.springboot.repository;

import mg.springboot.entity.Annonce;
import mg.springboot.entity.Favori;
import mg.springboot.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavoriRepository extends JpaRepository<Favori, Integer> {
    @Query("select f from Favori f where f.utilisateur = ?1 and f.etat = ?2")
    List<Favori> findByUtilisateurAndEtat(Utilisateur utilisateur, Integer etat);

    @Query("select f from Favori f where f.annonce = ?1 and f.utilisateur = ?2")
    Favori findByAnnonceAndUtilisateur(Annonce annonce, Utilisateur utilisateur);
}