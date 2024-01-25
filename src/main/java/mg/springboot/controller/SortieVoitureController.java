package mg.springboot.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.SortieVoiture;
import mg.springboot.security.Response;
import mg.springboot.service.SortieVoitureService;
import mg.springboot.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Setter
@Getter
public class SortieVoitureController {
    private final SortieVoitureService sortieVoitureService;
    private final TokenService tokenService;

    public SortieVoitureController(SortieVoitureService sortieVoitureService, TokenService tokenService) {
        this.sortieVoitureService = sortieVoitureService;
        this.tokenService = tokenService;
    }

    @GetMapping("/sortie_voitures/{id}")
    public Response<?> getSortieVoitures(@PathVariable int id) {
        return Response.send(HttpStatus.OK, "success", sortieVoitureService.findById(id));
    }


    @GetMapping("/sortie_voitures")
    public Response<?> getSortieVoitures()
    {
        return Response.send(HttpStatus.OK,"success",sortieVoitureService.getAll());
    }


    @PostMapping("/sortie_voitures")
    public Response<?> addSortieVoiture(@Valid SortieVoiture sortieVoiture) {
        return Response.send(HttpStatus.OK, "success", "La sortie de voiture a été ajouté",
                sortieVoitureService.save(sortieVoiture));
    }


    @PutMapping("/sortie_voitures/{id}")
    public Response<?> modifySortieVoiture(@PathVariable Integer id, @Valid SortieVoiture sortieVoiture) {
        return Response.send(HttpStatus.OK, "success", "La sortie de voiture a été modifié",
                sortieVoitureService.modify(id, sortieVoiture));
    }



    @DeleteMapping("/sortie_voitures/{id}")
    public Response<?> deleteSortieVoiture(@PathVariable Integer id) {
        return Response.send(HttpStatus.OK, "success", "La sortie de voiture a été supprimé",
                sortieVoitureService.delete(id));
    }
}

