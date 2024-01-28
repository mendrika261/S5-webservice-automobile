package mg.springboot.service;

import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.Commission;
import mg.springboot.exception.ValidationException;
import mg.springboot.repository.CommissionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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
        Set<Commission> resultats = new HashSet<>();
        if(commission2.getMinPrix() > commission1.getMaxPrix() ^ commission2.getMaxPrix() < commission1.getMinPrix()) {
            resultats.add(commission1);
            resultats.add(commission2);
            return resultats;
        }
        if(commission2.getMinPrix() <= commission1.getMinPrix() && commission2.getMaxPrix() >= commission1.getMaxPrix()) {
            resultats.add(commission2);
            return resultats;
        }
        if(commission2.getMinPrix() >= commission1.getMinPrix() && commission2.getMinPrix() <= commission1.getMaxPrix()) {
            Commission temp = new Commission(commission1.getMinPrix(), commission2.getMinPrix(), commission1.getPourcentage());
            resultats.add(temp);
            if(commission1.getMaxPrix() <= commission2.getMaxPrix()) {
                temp = new Commission(commission2.getMinPrix(), commission2.getMaxPrix(), commission2.getPourcentage());
                resultats.add(temp);
            } else {
                temp = new Commission(commission2.getMinPrix(), commission2.getMaxPrix(), commission2.getPourcentage());
                resultats.add(temp);
                temp = new Commission(commission2.getMaxPrix(), commission1.getMaxPrix(), commission1.getPourcentage());
                resultats.add(temp);
            }
            return resultats;
        }
        Commission temp = new Commission(commission2.getMinPrix(), commission2.getMaxPrix(), commission2.getPourcentage());
        resultats.add(temp);
        temp = new Commission(commission2.getMaxPrix(), commission1.getMaxPrix(), commission1.getPourcentage());
        resultats.add(temp);
        return resultats;
    }

    Set<Commission> removeDuplicated(List<Commission> commissions) {
        Set<Commission> resultats = new HashSet<Commission>();
        int id = 0;
        for(Commission commission : commissions) {
            boolean exist = false;
            for(Commission commission1 : resultats) {
                if(commission.equals(commission1)) {
                    exist = true;
                    break;
                }
            }
            if(!exist) {
                commission.setId(id);
                resultats.add(commission);
                id++;
            }
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
        return removeDuplicated(resultats).stream().sorted(Comparator.comparing(Commission::getMaxPrix).thenComparing(Commission::getMinPrix)).toList();
    }

    public Commission getCommission(LocalDateTime localDateTime, double valeur) {
        List<Commission> commissions = getCommissions(localDateTime);
        for(Commission commission : commissions) {
            if(valeur >= commission.getMinPrix() && valeur < commission.getMaxPrix())
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
