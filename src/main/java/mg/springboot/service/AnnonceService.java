package mg.springboot.service;

import jakarta.transaction.Transactional;
import mg.springboot.entity.*;
import mg.springboot.exception.ValidationException;
import mg.springboot.model.FilterObject;
import mg.springboot.repository.*;
import mg.springboot.repository.UtilisateurRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AnnonceService {
    private final AnnonceRepository annonceRepository;
    private final CommissionService commissionService;
    private final HistoriqueEtatAnnonceService historiqueEtatAnnonceService;
    private final UtilisateurRepository utilisateurRepository;
    private final EtatVoitureService etatVoitureService;
    private final CouleurService couleurService;
    private final MarqueService marqueService;

    private final NotificationService notificationService;
    private final BoiteVitesseRepository boiteVitesseRepository;
    private final EnergieRepository energieRepository;
    private final PaysRepository paysRepository;

    public AnnonceService(AnnonceRepository annonceRepository, CommissionService commissionService, HistoriqueEtatAnnonceService historiqueEtatAnnonceService, UtilisateurRepository utilisateurRepository, EtatVoitureService etatVoitureService, CouleurService couleurService, MarqueService marqueService, NotificationService notificationService,
                          BoiteVitesseRepository boiteVitesseRepository,
                          EnergieRepository energieRepository,
                          PaysRepository paysRepository) {
        this.annonceRepository = annonceRepository;
        this.commissionService = commissionService;
        this.historiqueEtatAnnonceService = historiqueEtatAnnonceService;
        this.utilisateurRepository = utilisateurRepository;
        this.etatVoitureService = etatVoitureService;
        this.couleurService = couleurService;
        this.marqueService = marqueService;
        this.notificationService = notificationService;
        this.boiteVitesseRepository = boiteVitesseRepository;
        this.energieRepository = energieRepository;
        this.paysRepository = paysRepository;
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

    @Transactional
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

        String imageUrl = annonce.getVoiture().getPhotos().stream().findFirst().isPresent() ?
                NotificationService.FIREBASE_IMG_URL+ URLEncoder.encode(annonce.getVoiture().getPhotos().stream().findFirst().get().getLien(), StandardCharsets.UTF_8)+"?alt=media" : null;


        notificationService.sendNotificationTo("Annonce approuvée",
                "Votre annonce sur la voiture "+annonce.getVoiture().getSortieVoiture().getModele().getMarque().getNom()+" " +
                        annonce.getVoiture().getSortieVoiture().getModele().getNom()+" a été validée", imageUrl,
                annonce.getVoiture().getUtilisateur().getId());

        return save(annonce);
    }

    @Transactional
    public Annonce refuser(Integer id, Utilisateur validateur) throws URISyntaxException {
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

        String imageUrl = annonce.getVoiture().getPhotos().stream().findFirst().isPresent() ?
                NotificationService.FIREBASE_IMG_URL+ URLEncoder.encode(annonce.getVoiture().getPhotos().stream().findFirst().get().getLien(), StandardCharsets.UTF_8)+"?alt=media" : null;

        notificationService.sendNotificationTo("Annonce refusée",
                "Votre annonce sur la voiture "+annonce.getVoiture().getSortieVoiture().getModele().getMarque().getNom()+" " +
                        annonce.getVoiture().getSortieVoiture().getModele().getNom()+" n'est pas conforme", imageUrl,
                annonce.getVoiture().getUtilisateur().getId());

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


    public List<Annonce> findAllValides(Integer page, Integer size, String sort, String order, FilterObject filterObject) {
        if(filterObject.getMarques().length==0)
        {
            List<Marque> marques=marqueService.getAll();
            filterObject.setMarques(marques.stream().map(Marque::getId).toArray(Integer[]::new));
        }
        if(filterObject.getBoiteVitesses().length==0)
        {
            List<BoiteVitesse> boiteVitesses=boiteVitesseRepository.findAll();
            filterObject.setBoiteVitesses(boiteVitesses.stream().map(BoiteVitesse::getId).toArray(Integer[]::new));
        }
        if(filterObject.getEnergie().length==0)
        {
            List<Energie> energies=energieRepository.findAll();
            filterObject.setEnergie(energies.stream().map(Energie::getId).toArray(Integer[]::new));
        }
        if(filterObject.getEtatVoiture().length==0)
        {
            List<EtatVoiture> etatVoitures=etatVoitureService.getAll();
            filterObject.setEtatVoiture(etatVoitures.stream().map(EtatVoiture::getId).toArray(Integer[]::new));
        }
        if(filterObject.getPays().length==0)
        {
            List<Pays> pays=paysRepository.findAll();
            filterObject.setPays(pays.stream().map(Pays::getId).toArray(Integer[]::new));
        }

        Sort.Direction direction = Sort.Direction.ASC;
        if (order.equalsIgnoreCase("desc"))
            direction = Sort.Direction.DESC;
        PageRequest pageable = PageRequest.of(page, size, Sort.by(direction, sort));
        return annonceRepository.findFilterAnnonceValide(filterObject.getModeleLike(), filterObject.getMarques(),
                        filterObject.getBoiteVitesses(), filterObject.getEnergie(), filterObject.getEtatVoiture(),
                        filterObject.getPays(), pageable)
                .stream()
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
        return annonceRepository.findAllByVoitureUtilisateurId(id).stream()
                .peek(annonce -> annonce
                        .setCommission(commissionService
                                .getCommission(annonce.getDate(), annonce.getPrix())))
                .toList();
    }

    public List<Annonce> findAllByIdUtilisateurAndValide(String id) {
        return annonceRepository.findAllByEtatAndVoitureUtilisateurId(Annonce.ETAT_VALIDE, id).stream()
                .peek(annonce -> annonce
                        .setCommission(commissionService
                                .getCommission(annonce.getDate(), annonce.getPrix())))
                .toList();
    }

    public Annonce modifierEtat(Integer id, Integer etat) {
        Annonce annonce = findById(id);
        annonce.setEtat(etat);
        return save(annonce);
    }
}
