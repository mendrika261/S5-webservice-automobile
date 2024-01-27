package mg.springboot.service;

import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.Couleur;
import mg.springboot.entity.Fichier;
import mg.springboot.exception.NotFoundException;
import mg.springboot.repository.FichierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Getter
@Setter
public class FichierService {
    FichierRepository fichierRepository;

    public FichierService(FichierRepository fichierRepository) {
        this.fichierRepository = fichierRepository;
    }

    public List<Fichier> findAll() {
        return fichierRepository.findAll();
    }

    public Fichier save(Fichier fichier) {
        return fichierRepository.save(fichier);
    }

    public Fichier findById(int id) {
        Optional<Fichier> fichier = fichierRepository.findById(id);
        if(fichier.isEmpty())
            throw new NotFoundException("Le fichier n'existe pas");
        return fichier.get();
    }

    public Fichier delete(int id) {
        Fichier fichier = findById(id);
        fichierRepository.delete(fichier);
        return fichier;
    }

}
