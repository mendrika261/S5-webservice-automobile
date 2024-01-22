package mg.springboot.service;

import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.Modele;
import mg.springboot.entity.Utilisateur;
import mg.springboot.exception.NotFoundException;
import mg.springboot.exception.ValidationException;
import mg.springboot.repository.ModeleRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Setter
@Getter
public class ModeleService {
    ModeleRepository modeleRepository;

    public ModeleService(ModeleRepository modeleRepository) {
        this.modeleRepository = modeleRepository;
    }

    public List<Modele> findAll() {
        return modeleRepository.findAll();
    }

    public Modele save(Modele modele) {
        return modeleRepository.save(modele);
    }

    public Modele findById(int id) {
        Optional<Modele> modele = modeleRepository.findById(id);
        if(modele.isEmpty())
            throw new NotFoundException("Le modele n'existe pas");
        return modele.get();
    }

//    public Modele findByMarqueAndNom()
//    {
//        return
//    }

    public Modele modify(int id, Modele modele) {
        Modele modifModele = findById(id); // throw exception si l'utilisateur n'existe pas
        modele.setId(modifModele.getId());
        return save(modele);
    }

    public Modele delete(int id) {
        Modele modele = findById(id);
        modeleRepository.delete(modele);
        return modele;
    }
}
