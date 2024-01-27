package mg.springboot.service;

import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.Annonce;
import mg.springboot.entity.Favori;
import mg.springboot.entity.Utilisateur;
import mg.springboot.entity.Vente;
import mg.springboot.exception.ValidationException;
import mg.springboot.repository.VenteRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Getter
@Setter
public class VenteService {
    VenteRepository venteRepository;
    AnnonceService annonceService;

    public VenteService(VenteRepository venteRepository, AnnonceService annonceService) {
        this.venteRepository = venteRepository;
        this.annonceService = annonceService;
    }

    public Vente save(Vente vente)
    {
        try {
            return venteRepository.save(vente);

        }catch (DataIntegrityViolationException e)
        {
            throw new ValidationException("Il y a déjà une vente effectuee pour cette annonce");
        }
    }


    public Vente findById(Integer id)
    {
        Optional<Vente> venteOptional = venteRepository.findById(id);
        if (venteOptional.isEmpty())
            throw new ValidationException("La vente n'existe pas n'existe pas");
        return venteOptional.get();
    }

    public Double getPrixVente(double pourcentageRemise,double prix)
    {
        double mihena=0;
        if(pourcentageRemise>0)
        {
            mihena=(prix*pourcentageRemise)/100;
        }
        return  prix-mihena;
    }

    public Vente finalizeSale(Vente vente)
    {
        if(vente.getRemise()!=null)
        {
            vente.setPrixVente(getPrixVente(vente.getRemise(),vente.getAnnonce().getPrix()));
        }
        if(vente.getUtilisateurAcheteur()==vente.getAnnonce().getVoiture().getUtilisateur())
        {
            throw new RuntimeException("Acheteur invalide");
        }

        Vente vente1=save(vente);
        annonceService.vendue(vente1.getAnnonce().getId());
        return vente1;
    }


}
