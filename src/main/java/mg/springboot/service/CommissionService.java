package mg.springboot.service;

import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.Commission;
import mg.springboot.exception.ValidationException;
import mg.springboot.repository.CommissionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Getter
@Setter
public class CommissionService {
    private final CommissionRepository commissionRepository;

    public CommissionService(CommissionRepository commissionRepository) {
        this.commissionRepository = commissionRepository;
    }

    public List<Commission> getHistorique()
    {
        return commissionRepository.findAllByOrderByDateApplicationDesc();
    }

    Set<Commission> fusionnerCommission(Commission commission1, Commission commission2) {
        Set<Commission> resultats = new HashSet<Commission>();
        if(commission2.getMinPrix() >= commission1.getMinPrix() && commission2.getMinPrix() <= commission1.getMaxPrix()) {
            if(commission2.getMaxPrix() <= commission1.getMaxPrix()) {
                resultats.add(new Commission(commission1.getMaxPrix(), commission2.getMaxPrix(), commission1.getPourcentage(), commission1.getDateApplication()));
                resultats.add(new Commission(commission2.getMaxPrix(), commission2.getMinPrix(), commission2.getPourcentage(), commission2.getDateApplication()));
                resultats.add(new Commission(commission2.getMinPrix(), commission1.getMinPrix(), commission1.getPourcentage(), commission1.getDateApplication()));
            } else {
                resultats.add(new Commission(commission1.getMaxPrix(), commission2.getMinPrix(), commission1.getPourcentage(), commission1.getDateApplication()));
                resultats.add(new Commission(commission2.getMinPrix(), commission1.getMinPrix(), commission2.getPourcentage(), commission2.getDateApplication()));
            }
        } else {
            resultats.add(commission1);
            resultats.add(commission2);
        }
        return resultats;
    }

    Set<Commission> removeDuplicated(List<Commission> commissions) {
        Set<Commission> resultats = new HashSet<Commission>();
        for(Commission commission : commissions) {
            boolean exist = false;
            for(Commission commission1 : resultats) {
                if(commission.equals(commission1)) {
                    exist = true;
                    break;
                }
            }
            if(!exist)
                resultats.add(commission);
        }
        return resultats;
    }

    public List<Commission> getCommissions(LocalDateTime dateApplication) {
        List<Commission> commissions = commissionRepository.findAllByDateApplicationBeforeOrderByDateApplicationAsc(dateApplication);
        List<Commission> resultats = new ArrayList<>();
        if(commissions.isEmpty())
            return resultats;
        resultats.add(commissions.get(0));
        for(int i = 1; i < commissions.size(); i++) {
            List<Commission> resultatsTemp = new ArrayList<>();
            for(Commission commission : resultats) {
                resultatsTemp.addAll(fusionnerCommission(commission, commissions.get(i)));
            }
            resultats = resultatsTemp;
        }
        return removeDuplicated(resultats).stream().sorted((o1, o2) -> {
            return o1.getMaxPrix().compareTo(o2.getMaxPrix());
        }).toList();
    }

    public Commission getCommission(LocalDateTime localDateTime, double valeur) {
        List<Commission> commissions = getCommissions(localDateTime);
        for(Commission commission : commissions) {
            if(valeur >= commission.getMinPrix() && valeur <= commission.getMaxPrix())
                return commission;
        }
        return null;
    }

    public Commission save(Commission commission)
    {
        if(commission.getMinPrix() > commission.getMaxPrix())
            throw new ValidationException("Le prix minimum doit être inférieur au prix maximum");
        return commissionRepository.save(commission);
    }

    public Commission findById(Integer id) {
        return commissionRepository.findById(id).orElseThrow(() -> new ValidationException("La commission n'existe pas"));
    }

    public Commission delete(Integer id) {
        Commission commission = findById(id);
        commissionRepository.delete(commission);
        return commission;
    }
}
