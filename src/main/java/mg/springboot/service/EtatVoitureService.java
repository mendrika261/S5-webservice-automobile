package mg.springboot.service;

import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.EtatVoiture;
import mg.springboot.exception.ValidationException;
import mg.springboot.repository.EtatVoitureRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@Setter
public class EtatVoitureService {
    EtatVoitureRepository etatVoitureRepository;

    public EtatVoitureService(EtatVoitureRepository etatVoitureRepository) {
        this.etatVoitureRepository = etatVoitureRepository;
    }

    public List<EtatVoiture> getAll()
    {
        return etatVoitureRepository.findAll();
    }

    public EtatVoiture save(EtatVoiture etatVoiture)
    {
        try {
            return etatVoitureRepository.save(etatVoiture);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Il y a déjà un état de voiture de ce nom ");
        }
    }

    public EtatVoiture findById(Integer id) {
        return etatVoitureRepository.findById(id).orElseThrow(() -> new ValidationException("L'état de voiture n'existe pas"));
    }

    public EtatVoiture modify(Integer id, EtatVoiture etatVoiture) {
        EtatVoiture modifEtatVoiture = findById(id);
        etatVoiture.setId(modifEtatVoiture.getId());
        return save(etatVoiture);
    }

    public EtatVoiture delete(Integer id) {
        EtatVoiture etatVoiture = findById(id);
        etatVoitureRepository.delete(etatVoiture);
        return etatVoiture;
    }
}
