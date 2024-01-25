package mg.springboot.service;

import mg.springboot.entity.Voiture;
import mg.springboot.exception.NotFoundException;
import mg.springboot.exception.ValidationException;
import mg.springboot.repository.VoitureRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoitureService {
    private final VoitureRepository voitureRepository;

    public VoitureService(VoitureRepository voitureRepository) {
        this.voitureRepository = voitureRepository;
    }

    public List<Voiture> findAll() {
        return voitureRepository.findAll();
    }

    public Voiture findById(Integer id) {
        Optional<Voiture> voiture = voitureRepository.findById(id);
        if (voiture.isEmpty())
            throw new NotFoundException("La voiture n'existe pas");
        return voiture.get();
    }

    public Voiture save(Voiture voiture) {
        if(voiture.getSortieVoiture().getAnnee() > voiture.getMiseEnCirculation())
            throw new ValidationException("La date de mise en circulation ne peut pas être antérieure à la date de sortie");
        try {
            return voitureRepository.save(voiture);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Il y a déjà une voiture immatriculée de ce numéro");
        }
    }

    public Voiture modify(Integer id, Voiture voiture) {
        Voiture modifVoiture = findById(id);
        voiture.setId(modifVoiture.getId());
        return save(voiture);
    }

    public Voiture delete(Integer id) {
        Voiture voiture = findById(id);
        voitureRepository.delete(voiture);
        return voiture;
    }
}
