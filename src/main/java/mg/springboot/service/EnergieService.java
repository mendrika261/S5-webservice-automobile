package mg.springboot.service;

import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.Energie;
import mg.springboot.entity.Pays;
import mg.springboot.exception.NotFoundException;
import mg.springboot.repository.EnergieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Setter
@Getter
public class EnergieService {
    EnergieRepository energieRepository;

    public EnergieService(EnergieRepository energieRepository) {
        this.energieRepository = energieRepository;
    }

    public List<Energie> findAll() {
        return energieRepository.findAll();
    }

    public Energie save(Energie energie) {
        return energieRepository.save(energie);
    }

    public Energie findById(int id) {
        Optional<Energie> energie = energieRepository.findById(id);
        if(energie.isEmpty())
            throw new NotFoundException("Le pays n'existe pas");
        return energie.get();
    }


    public Energie modify(int id, Energie energie) {
        Energie modifEnergie = findById(id); // throw exception si le pays n'existe pas
        modifEnergie.setId(modifEnergie.getId());
        return save(energie);
    }

    public Energie delete(int id) {
        Energie energie = findById(id);
        energieRepository.delete(energie);
        return energie;
    }
}
