package mg.springboot.service;

import mg.springboot.entity.*;
import mg.springboot.exception.ValidationException;
import mg.springboot.repository.AnnonceRepository;
import mg.springboot.repository.EtatVoitureRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AnnonceService {
    private final AnnonceRepository annonceRepository;
    private final CommissionService commissionService;
    private final HistoriqueEtatAnnonceService historiqueEtatAnnonceService;
    private final EtatVoitureService etatVoitureService;
    private final CouleurService couleurService;
    private final MarqueService marqueService;

    public AnnonceService(AnnonceRepository annonceRepository, CommissionService commissionService, HistoriqueEtatAnnonceService historiqueEtatAnnonceService, EtatVoitureService etatVoitureService, CouleurService couleurService, MarqueService marqueService) {
        this.annonceRepository = annonceRepository;
        this.commissionService = commissionService;
        this.historiqueEtatAnnonceService = historiqueEtatAnnonceService;
        this.etatVoitureService = etatVoitureService;
        this.couleurService = couleurService;
        this.marqueService = marqueService;
    }

    public List<Annonce> findAll() {
        return annonceRepository.findAll().stream()
                .peek(annonce -> annonce
                    .setCommission(commissionService
                    .getCommission(annonce.getDate(), annonce.getPrix())))
                .toList();
    }

    public List<Annonce> findAllEnAttente() {
        return annonceRepository.findAllByEtat(Annonce.ETAT_NON_VALIDE).stream()
                .peek(annonce -> annonce
                    .setCommission(commissionService
                    .getCommission(annonce.getDate(), annonce.getPrix())))
                .toList();
    }

    public Annonce valider(Integer id, Utilisateur validateur) {
        Annonce annonce = findById(id);
        annonce.setEtat(Annonce.ETAT_VALIDE);
        annonce.setValidateur(validateur);
        annonce.setDateValidation(LocalDateTime.now());


        //historiser
        HistoriqueEtatAnnonce historiqueEtatAnnonce1=new HistoriqueEtatAnnonce();
        historiqueEtatAnnonce1.setAnnonce(annonce);
        historiqueEtatAnnonce1.setData_action(LocalDateTime.now());
        historiqueEtatAnnonce1.setEtat(Annonce.ETAT_VALIDE);
        historiqueEtatAnnonce1.setUtilisateur_origine(validateur);
        historiqueEtatAnnonceService.save(historiqueEtatAnnonce1);
        return save(annonce);
    }

    public Annonce refuser(Integer id, Utilisateur validateur) {
        Annonce annonce = findById(id);
        annonce.setEtat(Annonce.ETAT_REFUSE);
        annonce.setValidateur(validateur);
        annonce.setDateValidation(LocalDateTime.now());

        HistoriqueEtatAnnonce historiqueEtatAnnonce1=new HistoriqueEtatAnnonce();
        historiqueEtatAnnonce1.setAnnonce(annonce);
        historiqueEtatAnnonce1.setData_action(LocalDateTime.now());
        historiqueEtatAnnonce1.setEtat(Annonce.ETAT_REFUSE);
        historiqueEtatAnnonce1.setUtilisateur_origine(validateur);
        historiqueEtatAnnonceService.save(historiqueEtatAnnonce1);
        return save(annonce);
    }

    public Annonce findById(Integer id) {
        Optional<Annonce> annonceOptional = annonceRepository.findById(id);
        if (annonceOptional.isEmpty())
            throw new ValidationException("L'annonce n'existe pas");
        Annonce annonce = annonceOptional.get();
        annonce.setCommission(commissionService.getCommission(annonce.getDate(), annonce.getPrix()));
        return annonce;
    }

    public Annonce save(Annonce annonce) {
        try {

            Annonce annonce1= annonceRepository.save(annonce);
            if(annonce.getEtat()==Annonce.ETAT_NON_VALIDE)
            {
                HistoriqueEtatAnnonce historiqueEtatAnnonce1=new HistoriqueEtatAnnonce();
                historiqueEtatAnnonce1.setAnnonce(annonce1);
                historiqueEtatAnnonce1.setData_action(LocalDateTime.now());
                historiqueEtatAnnonce1.setEtat(annonce.getEtat());
                historiqueEtatAnnonce1.setUtilisateur_origine(annonce1.getVoiture().getUtilisateur());
                historiqueEtatAnnonceService.save(historiqueEtatAnnonce1);
            }
            return  annonce1;
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Il y a déjà une annonce pour cette voiture");
        }
    }


    public Annonce vendue(int id)
    {
        Annonce annonce = findById(id);
        annonce.setEtat(Annonce.ETAT_VENDUE);


        //historiser
        HistoriqueEtatAnnonce historiqueEtatAnnonce1=new HistoriqueEtatAnnonce();
        historiqueEtatAnnonce1.setAnnonce(annonce);
        historiqueEtatAnnonce1.setData_action(LocalDateTime.now());
        historiqueEtatAnnonce1.setEtat(Annonce.ETAT_VENDUE);
        historiqueEtatAnnonce1.setUtilisateur_origine(annonce.getVoiture().getUtilisateur());
        historiqueEtatAnnonceService.save(historiqueEtatAnnonce1);
        return save(annonce);
    }

    public Annonce modify(Integer id, Annonce annonce) {
        Annonce modifAnnonce = findById(id);
        annonce.setId(modifAnnonce.getId());
        return save(annonce);
    }

    public List<Annonce> test_filter(FilterRequest filterRequest)
    {
        EtatVoiture[] etatVoitures=filterRequest.getEtatVoitures();
        Couleur[] couleurs=filterRequest.getCouleurs();
        Marque[] marques=filterRequest.getMarques();
         if(etatVoitures.length==0)
        {
            List<EtatVoiture> etatVoituresL=etatVoitureService.getAll();
            etatVoitures = new EtatVoiture[etatVoituresL.size()];
            for (int i = 0; i < etatVoituresL.size(); i++) {
                etatVoitures[i] = etatVoituresL.get(i);
            }
        }
        if(couleurs.length==0)
        {
            List<Couleur> couleurs1=couleurService.findAll();
            couleurs = new Couleur[couleurs1.size()];
            for (int i = 0; i < couleurs1.size(); i++) {
                couleurs[i] = couleurs1.get(i);
            }
        }

        if(marques.length==0)
        {
            List<Marque> marques1=marqueService.getAll();
            marques = new Marque[marques1.size()];
            for (int i = 0; i < marques1.size(); i++) {
                marques[i] = marques1.get(i);
            }
        }
        return annonceRepository.findByVoiture_EtatVoitureInAndVoiture_CouleurInAndVoiture_SortieVoitureIn(Arrays.asList(etatVoitures),Arrays.asList(couleurs),Arrays.asList(marques));
    }

    public Annonce delete(Integer id) {
        Annonce annonce = findById(id);
        annonceRepository.delete(annonce);

        return annonce;
    }

    public List<Annonce> findAllValides()
    {
        return annonceRepository.findAllByEtat(Annonce.ETAT_VALIDE).stream()
                .peek(annonce -> annonce
                        .setCommission(commissionService
                                .getCommission(annonce.getDate(), annonce.getPrix())))
                .toList();
    }

    public List<Annonce> findAllValides(Utilisateur utilisateur)
    {
        return annonceRepository.findByEtatAndVoiture_UtilisateurNot(Annonce.ETAT_VALIDE,utilisateur).stream()
                .peek(annonce -> annonce
                        .setCommission(commissionService
                                .getCommission(annonce.getDate(), annonce.getPrix())))
                .toList();
    }


}
