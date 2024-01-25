package mg.springboot.service;

import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.BoiteVitesse;
import mg.springboot.entity.Pays;
import mg.springboot.exception.NotFoundException;
import mg.springboot.repository.BoiteVitesseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Setter
@Getter
public class BoiteVitesseService {
    BoiteVitesseRepository boiteVitesseRepository;

    public BoiteVitesseService(BoiteVitesseRepository boiteVitesseRepository) {
        this.boiteVitesseRepository = boiteVitesseRepository;
    }

    public List<BoiteVitesse> findAll() {
        return boiteVitesseRepository.findAll();
    }

    public BoiteVitesse save(BoiteVitesse boiteVitesse) {
        return boiteVitesseRepository.save(boiteVitesse);
    }

    public BoiteVitesse findById(int id) {
        Optional<BoiteVitesse> pays = boiteVitesseRepository.findById(id);
        if(pays.isEmpty())
            throw new NotFoundException("La boite de vitesse n'existe pas");
        return pays.get();
    }


    public BoiteVitesse modify(int id, BoiteVitesse boiteVitesse) {
        BoiteVitesse modifBoiteVitesse = findById(id); // throw exception si la boiteVitesse n'existe pas
        modifBoiteVitesse.setId(modifBoiteVitesse.getId());
        return save(boiteVitesse);
    }

    public BoiteVitesse delete(int id) {
        BoiteVitesse boiteVitesse = findById(id);
        boiteVitesseRepository.delete(boiteVitesse);
        return boiteVitesse;
    }
}
