package mg.springboot.service;

import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.Paiement;
import mg.springboot.exception.ValidationException;
import mg.springboot.repository.PaiementRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@Setter
public class PaiementService {
    PaiementRepository paiementRepository;

    public PaiementService(PaiementRepository paiementRepository) {
        this.paiementRepository = paiementRepository;
    }

    public List<Paiement> getAll()
    {
        return paiementRepository.findAll();
    }

    public Paiement save(Paiement paiement)
    {
        try {
            return paiementRepository.save(paiement);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Il y a déjà un mode de paiement de ce nom ");
        }
    }

    public Paiement findById(Integer id) {
        return paiementRepository.findById(id).orElseThrow(() -> new ValidationException("Le mode de paiement n'existe pas"));
    }

    public Paiement modify(Integer id, Paiement paiement) {
        Paiement modifPaiement = findById(id);
        paiement.setId(modifPaiement.getId());
        return save(paiement);
    }

    public Paiement delete(Integer id) {
        Paiement paiement = findById(id);
        paiementRepository.delete(paiement);
        return paiement;
    }
}
