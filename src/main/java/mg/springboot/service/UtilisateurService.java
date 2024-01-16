package mg.springboot.service;

import jdk.jshell.execution.Util;
import mg.springboot.entity.Utilisateur;
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
        return utilisateurRepository.save(utilisateur);
    }
}
