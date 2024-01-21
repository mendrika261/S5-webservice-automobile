package mg.springboot.controller;

import jakarta.validation.Valid;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.Marque;
import mg.springboot.entity.Utilisateur;
import mg.springboot.exception.NotFoundException;
import mg.springboot.security.Response;
import mg.springboot.service.MarqueService;
import mg.springboot.service.TokenService;
import mg.springboot.service.UtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Setter
@Getter
public class MarqueController {
  private final MarqueService marqueService;
  private final TokenService tokenService;

  public MarqueController(MarqueService marqueService,
                          TokenService tokenService) {
    this.marqueService = marqueService;
    this.tokenService = tokenService;
  }

  @GetMapping("/marques/{id}")
  public Response<?> getMarques(@PathVariable int id) {
    return Response.send(HttpStatus.OK, "success", marqueService.findById(id));
  }

  @GetMapping("/marques")
  public Response<?> getMarques() {
    return Response.send(HttpStatus.OK, "success", marqueService.getAll());
  }

  @PostMapping("/marques")
  public Response<?> addMarque(@Valid Marque marque) {
    return Response.send(HttpStatus.OK, "success", "La marque  a été ajouté",
                         marqueService.save(marque));
  }

  @PutMapping("/marques/{id}")
  public Response<?> modifyMarque(@PathVariable Integer id,
                                  @Valid Marque marque) {
    return Response.send(HttpStatus.OK, "success", "La marque a été modifié",
                         marqueService.modify(id, marque));
  }

  @DeleteMapping("/marques/{id}")
  public Response<?> deleteMarque(@PathVariable Integer id) {
    return Response.send(HttpStatus.OK, "success", "La marque a été supprimé",
                         marqueService.delete(id));
  }
}
