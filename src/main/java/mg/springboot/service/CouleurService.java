package mg.springboot.service;

import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.Couleur;
import mg.springboot.entity.Energie;
import mg.springboot.exception.NotFoundException;
import mg.springboot.repository.CouleurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Setter
@Getter
public class CouleurService {
    CouleurRepository couleurRepository;

    public CouleurService(CouleurRepository couleurRepository) {
        this.couleurRepository = couleurRepository;
    }

    public List<Couleur> findAll() {
        return couleurRepository.findAll();
    }

    public Couleur save(Couleur couleur) {
        return couleurRepository.save(couleur);
    }

    public Couleur findById(int id) {
        Optional<Couleur> couleur = couleurRepository.findById(id);
        if(couleur.isEmpty())
            throw new NotFoundException("La couleur n'existe pas");
        return couleur.get();
    }


    public Couleur modify(int id, Couleur couleur) {
        Couleur modifCouleur = findById(id);
        modifCouleur.setId(modifCouleur.getId());
        return save(couleur);
    }

    public Couleur delete(int id) {
        Couleur couleur = findById(id);
        couleurRepository.delete(couleur);
        return couleur;
    }
}
