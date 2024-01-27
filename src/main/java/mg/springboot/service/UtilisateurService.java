package mg.springboot.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import mg.springboot.entity.Utilisateur;
import mg.springboot.exception.NotFoundException;
import mg.springboot.exception.ValidationException;
import mg.springboot.repository.UtilisateurRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;
    private final Validator validator;

    public UtilisateurService(UtilisateurRepository utilisateurRepository, Validator validator) {
        this.utilisateurRepository = utilisateurRepository;
        this.validator = validator;
    }

    public Optional<Utilisateur> findByEmailAndMotDePasse(String login, String motDePasse) {
        if(login == null || login.isEmpty())
            throw new ValidationException("L'email doit être renseigné");
        if(motDePasse == null || motDePasse.isEmpty())
            throw new ValidationException("Le mot de passe doit être renseigné");
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
        Utilisateur modifUtilisateur = findById(id);
        utilisateur.setId(modifUtilisateur.getId());

        if(utilisateur.getMotDePasse() == null)
            utilisateur.setMotDePasse(modifUtilisateur.getMotDePasse());

        if(utilisateur.getPhoto() == null)
            utilisateur.setPhoto(modifUtilisateur.getPhoto());

        Set<ConstraintViolation<Utilisateur>> violations = validator.validate(utilisateur);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

        return save(utilisateur);
    }

    public Utilisateur delete(String id) {
        Utilisateur utilisateur = findById(id);
        try {
            utilisateurRepository.delete(utilisateur);
            System.out.println(utilisateur.getEmail());
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Vous ne pouvez pas supprimer l'utilisateur connecté");
        }
        return utilisateur;
    }
}
