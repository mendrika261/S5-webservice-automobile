package mg.springboot.service;

import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.Marque;
import mg.springboot.entity.Utilisateur;
import mg.springboot.exception.NotFoundException;
import mg.springboot.exception.ValidationException;
import mg.springboot.repository.MarqueRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Setter
@Getter
public class MarqueService {
  MarqueRepository marqueRepository;

  public MarqueService(MarqueRepository marqueRepository) {
    this.marqueRepository = marqueRepository;
  }

  public List<Marque> getAll() { return marqueRepository.findAll(); }

  public Marque save(Marque marque) {
    try {
      return marqueRepository.save(marque);
    } catch (DataIntegrityViolationException e) {
      throw new ValidationException("Il y a déjà une marque de ce nom ");
    }
  }

  public Marque findById(Integer id) {
    Optional<Marque> marque = marqueRepository.findById(id);
    if (marque.isEmpty())
      throw new NotFoundException("La marque n'existe pas");
    return marque.get();
  }

  public Marque modify(Integer id, Marque marque) {
    Marque modifMarque =
        findById(id); // throw exception si l'utilisateur n'existe pas
    marque.setId(modifMarque.getId());
    return save(marque);
  }

  public Marque delete(Integer id) {
    Marque marque = findById(id);
    marqueRepository.delete(marque);
    return marque;
  }
}
