package mg.springboot.service;

import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.Annonce;
import mg.springboot.entity.HistoriqueEtatAnnonce;
import mg.springboot.exception.ValidationException;
import mg.springboot.repository.HistoriqueEtatAnnonceRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Setter
@Getter
@Service
public class HistoriqueEtatAnnonceService {
    HistoriqueEtatAnnonceRepository historiqueEtatAnnonceRepository;

    public HistoriqueEtatAnnonceService(HistoriqueEtatAnnonceRepository historiqueEtatAnnonceRepository) {
        this.historiqueEtatAnnonceRepository = historiqueEtatAnnonceRepository;
    }

    public HistoriqueEtatAnnonce save(HistoriqueEtatAnnonce historiqueEtatAnnonce) {
        return historiqueEtatAnnonceRepository.save(historiqueEtatAnnonce);
    }


    public List<HistoriqueEtatAnnonce> getAllByAnnonce(Annonce annonce)
    {
        return historiqueEtatAnnonceRepository.findByAnnonce(annonce);
    }


}
