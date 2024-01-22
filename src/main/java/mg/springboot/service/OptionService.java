package mg.springboot.service;

import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.Energie;
import mg.springboot.entity.Option;
import mg.springboot.exception.NotFoundException;
import mg.springboot.repository.OptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Setter
@Service
@Getter
public class OptionService {
    OptionRepository optionRepository;

    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public List<Option> findAll() {
        return optionRepository.findAll();
    }

    public Option save(Option option) {
        return optionRepository.save(option);
    }

    public Option findById(int id) {
        Optional<Option> option = optionRepository.findById(id);
        if(option.isEmpty())
            throw new NotFoundException("L' option n'existe pas");
        return option.get();
    }


    public Option modify(int id, Option option) {
        Option modifOption = findById(id); // throw exception si l'option n'existe pas
        modifOption.setId(modifOption.getId());
        return save(option);
    }

    public Option delete(int id) {
        Option option = findById(id);
        optionRepository.delete(option);
        return option;
    }
}
