package mg.springboot.service;

import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.Modele;
import mg.springboot.entity.Pays;
import mg.springboot.exception.NotFoundException;
import mg.springboot.repository.PaysRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Setter
@Getter
public class PaysService {
    PaysRepository paysRepository;

    public PaysService(PaysRepository paysRepository) {
        this.paysRepository = paysRepository;
    }

    public List<Pays> findAll() {
        return paysRepository.findAll();
    }

    public Pays save(Pays pays) {
        return paysRepository.save(pays);
    }

    public Pays findById(int id) {
        Optional<Pays> pays = paysRepository.findById(id);
        if(pays.isEmpty())
            throw new NotFoundException("Le pays n'existe pas");
        return pays.get();
    }


    public Pays modify(int id, Pays pays) {
        Pays modifPays = findById(id); // throw exception si le pays n'existe pas
        modifPays.setId(modifPays.getId());
        return save(pays);
    }

    public Pays delete(int id) {
        Pays pays = findById(id);
        paysRepository.delete(pays);
        return pays;
    }
}
