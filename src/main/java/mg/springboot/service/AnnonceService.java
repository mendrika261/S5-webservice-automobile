package mg.springboot.service;

import mg.springboot.entity.Annonce;
import mg.springboot.entity.HistoriqueEtatAnnonce;
import mg.springboot.entity.Utilisateur;
import mg.springboot.exception.ValidationException;
import mg.springboot.repository.AnnonceRepository;
import mg.springboot.repository.UtilisateurRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AnnonceService {
    private final AnnonceRepository annonceRepository;
    private final CommissionService commissionService;
    private final HistoriqueEtatAnnonceService historiqueEtatAnnonceService;
    private final UtilisateurRepository utilisateurRepository;

    public AnnonceService(AnnonceRepository annonceRepository, CommissionService commissionService, HistoriqueEtatAnnonceService historiqueEtatAnnonceService,
                          UtilisateurRepository utilisateurRepository) {
        this.annonceRepository = annonceRepository;
        this.commissionService = commissionService;
        this.historiqueEtatAnnonceService = historiqueEtatAnnonceService;
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<Annonce> findAll() {
        return annonceRepository.findAll().stream()
                .peek(annonce -> annonce
                    .setCommission(commissionService
                    .getCommission(annonce.getDate(), annonce.getPrix())))
                .toList();
    }

    public List<Annonce> findAllEnAttente(Integer page, Integer size) {
        PageRequest pageable = PageRequest.of(page, size);
        return annonceRepository.findAllByEtat(Annonce.ETAT_NON_VALIDE, pageable).stream()
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

    public Annonce delete(Integer id) {
        Annonce annonce = findById(id);
        annonceRepository.delete(annonce);

        return annonce;
    }

    public List<Annonce> findAllValides(Integer page, Integer size, String sort, String order) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (order.equalsIgnoreCase("desc"))
            direction = Sort.Direction.DESC;
        PageRequest pageable = PageRequest.of(page, size, Sort.by(direction, sort));
        return annonceRepository.findAllByEtat(Annonce.ETAT_VALIDE, pageable).stream()
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


    public List<Annonce> findAllByIdUtilisateur(String id) {
        return annonceRepository.findAllByVoitureUtilisateurId(id);
    }

    public List<Annonce> findAllByIdUtilisateurAndValide(String id) {
        return annonceRepository.findAllByEtatAndVoitureUtilisateurId(Annonce.ETAT_VALIDE, id);
    }

    public Annonce modifierEtat(Integer id, Integer etat) {
        Annonce annonce = findById(id);
        if (etat != Annonce.ETAT_VENDUE)
            throw new ValidationException("Vous ne pouvez pas modifier l'état de l'annonce autre que vendue");
        annonce.setEtat(etat);
        return save(annonce);
    }
}
