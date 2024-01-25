package mg.springboot.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.Energie;
import mg.springboot.entity.Option;
import mg.springboot.security.Response;
import mg.springboot.service.OptionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Getter
@Setter
public class OptionController {
    OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping("/options")
    public Response<?> getOptions() {
        return Response.send(HttpStatus.OK, "success", optionService.findAll());
    }

    @GetMapping("/options/{id}")
    public Response<?> getOptions(@PathVariable int id) {
        return Response.send(HttpStatus.OK, "success", optionService.findById(id));
    }

    @PostMapping("/options")
    public Response<?> addOptions(@Valid Option option) {
        return Response.send(HttpStatus.OK, "success", "L'option a été ajouté",
                optionService.save(option));
    }

    @PutMapping("/options/{id}")
    public Response<?> modifyOptions(@PathVariable int id, @Valid Option option) {
        return Response.send(HttpStatus.OK, "success", "L'option a été modifié",
                optionService.modify(id, option));
    }

    @DeleteMapping("/options/{id}")
    public Response<?> deleteOptions(@PathVariable int id) {
        return Response.send(HttpStatus.OK, "success", "L'option a été supprimé",
                optionService.delete(id));
    }
}
