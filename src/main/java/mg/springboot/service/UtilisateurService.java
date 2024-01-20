package mg.springboot.service;

import mg.springboot.entity.Utilisateur;
import mg.springboot.exception.NotFoundException;
import mg.springboot.exception.ValidationException;
import mg.springboot.repository.UtilisateurRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public Optional<Utilisateur> findByEmailAndMotDePasse(String login, String motDePasse) {
        return utilisateurRepository.findByEmailAndMotDePasse(login, motDePasse);
    }

    public List<Utilisateur> findAll() {
        return utilisateurRepository.findAll();
    }

    public Utilisateur save(Utilisateur utilisateur) {
        try {
            return utilisateurRepository.save(utilisateur);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Il y a déjà un compte associé à cet email");
        }
    }

    public Utilisateur findById(String id) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
        if(utilisateur.isEmpty())
            throw new NotFoundException("L'utilisateur n'existe pas");
        return utilisateur.get();
    }

    public Utilisateur modify(String id, Utilisateur utilisateur) {
        Utilisateur modifUtilisateur = findById(id); // throw exception si l'utilisateur n'existe pas
        utilisateur.setId(modifUtilisateur.getId());
        return save(utilisateur);
    }

    public Utilisateur delete(String id) {
        Utilisateur utilisateur = findById(id);
        utilisateurRepository.delete(utilisateur);
        return utilisateur;
    }
}
