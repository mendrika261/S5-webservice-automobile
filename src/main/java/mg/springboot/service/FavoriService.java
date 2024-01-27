package mg.springboot.service;

import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.Annonce;
import mg.springboot.entity.Favori;
import mg.springboot.entity.HistoriqueEtatAnnonce;
import mg.springboot.entity.Utilisateur;
import mg.springboot.exception.ValidationException;
import mg.springboot.repository.FavoriRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Setter
@Getter
@Service
public class FavoriService {

    FavoriRepository favoriRepository;
    public FavoriService(FavoriRepository favoriRepository) {
        this.favoriRepository = favoriRepository;
    }

    public List<Favori> findAll(Utilisateur utilisateur) {
        return favoriRepository.findByUtilisateurAndEtat(utilisateur,Favori.ETAT_VALIDE);
    }


    public Favori findByUtilisateurAndAnnonce(Utilisateur utilisateur,Annonce annonce)
    {
        Favori favori=favoriRepository.findByAnnonceAndUtilisateur(annonce,utilisateur);
        return favori;
    }

    public Favori addFavorite(Utilisateur utilisateur,Annonce annonce)
    {
        if(utilisateur==null)
        {
            throw new RuntimeException("l'utilisateur n'est pas connnecte");
        }
        if(annonce==null)
        {
            throw new RuntimeException("l'annonce n'existe pas");
        }
        Favori favori=findByUtilisateurAndAnnonce(utilisateur,annonce);
        if(favori==null)
        {
            favori=new Favori();
            favori.setAnnonce(annonce);
            favori.setUtilisateur(utilisateur);
        }
        favori.setDate_action(LocalDateTime.now());
        favori.setEtat(Favori.ETAT_VALIDE);
        return favori;
    }


    public Favori removeFavorite(Utilisateur utilisateur,Annonce annonce)
    {
        if(utilisateur==null)
        {
            throw new RuntimeException("l'utilisateur n'est pas connnecte");
        }
        if(annonce==null)
        {
            throw new RuntimeException("l'annonce n'existe pas");
        }
        Favori favori=findByUtilisateurAndAnnonce(utilisateur,annonce);
        if(favori==null)
        {
            throw new RuntimeException("le favori n'existe pas");
        }
        favori.setDate_action(LocalDateTime.now());
        favori.setEtat(Favori.ETAT_NON_VALIDE);
        return favori;
    }

    public Favori findById(Integer id) {
        Optional<Favori> favoriOptional = favoriRepository.findById(id);
        if (favoriOptional.isEmpty())
            throw new ValidationException("Le favori n'existe pas n'existe pas");
        return favoriOptional.get();
    }


}
