package mg.springboot.service;

import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.SortieVoiture;
import mg.springboot.exception.ValidationException;
import mg.springboot.repository.SortieVoitureRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@Setter
public class SortieVoitureService {
    SortieVoitureRepository sortieVoitureRepository;

    public SortieVoitureService(SortieVoitureRepository sortieVoitureRepository) {
        this.sortieVoitureRepository = sortieVoitureRepository;
    }

    public List<SortieVoiture> getAll()
    {
        return sortieVoitureRepository.findAll();
    }

    public SortieVoiture save(SortieVoiture sortieVoiture)
    {
        try {
            return sortieVoitureRepository.save(sortieVoiture);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("La sortie de voiture existe déjà");
        }
    }

    public SortieVoiture findById(Integer id) {
        return sortieVoitureRepository.findById(id).orElseThrow(() -> new ValidationException("La sortie de voiture n'existe pas"));
    }

    public SortieVoiture modify(Integer id, SortieVoiture sortieVoiture) {
        SortieVoiture modifSortieVoiture = findById(id);
        sortieVoiture.setId(modifSortieVoiture.getId());
        return save(sortieVoiture);
    }

    public SortieVoiture delete(Integer id) {
        SortieVoiture sortieVoiture = findById(id);
        sortieVoitureRepository.delete(sortieVoiture);
        return sortieVoiture;
    }
}
