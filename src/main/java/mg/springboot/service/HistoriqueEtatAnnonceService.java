package mg.springboot.service;

import lombok.Getter;
import lombok.Setter;
import mg.springboot.repository.HistoriqueEtatAnnonceRepository;
import org.springframework.stereotype.Service;

@Setter
@Getter
@Service
public class HistoriqueEtatAnnonceService {
    HistoriqueEtatAnnonceRepository historiqueEtatAnnonceRepository;

    public HistoriqueEtatAnnonceService(HistoriqueEtatAnnonceRepository historiqueEtatAnnonceRepository) {
        this.historiqueEtatAnnonceRepository = historiqueEtatAnnonceRepository;
    }
}
