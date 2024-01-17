package mg.springboot.service;

import jdk.jshell.execution.Util;
import mg.springboot.entity.Utilisateur;
import mg.springboot.exception.ValidationException;
import mg.springboot.repository.UtilisateurRepository;
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
        utilisateur.validate();
        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur modify(Utilisateur utilisateur) {
        if(!utilisateurRepository.existsById(utilisateur.getId()))
            throw new ValidationException("L'utilisateur n'existe pas");
        return save(utilisateur);
    }

    public Utilisateur delete(String id) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
        if(utilisateur.isEmpty())
            throw new ValidationException("L'utilisateur n'existe pas");
        utilisateurRepository.delete(utilisateur.get());
        return utilisateur.get();
    }
}
