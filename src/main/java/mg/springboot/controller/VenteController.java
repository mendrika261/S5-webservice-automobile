package mg.springboot.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.Vente;
import mg.springboot.security.Response;
import mg.springboot.security.Token;
import mg.springboot.service.TokenService;
import mg.springboot.service.VenteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Getter
@Setter
public class VenteController {
    TokenService tokenService;
    VenteService venteService;

    public VenteController(TokenService tokenService, VenteService venteService) {
        this.tokenService = tokenService;
        this.venteService = venteService;
    }

    @PostMapping("/ventes")
    public Response<?> finaliserVente(@Valid Vente vente, HttpServletRequest request)
    {
        Token token=tokenService.getToken(request);
        if(token==null)
        {
            return Response.send(HttpStatus.OK, "error", "aucun utilisateur connecte");
        }
        if(token.getUtilisateur()!=vente.getAnnonce().getVoiture().getUtilisateur())
        {
            return Response.send(HttpStatus.OK, "error", "Vous ne pouvez pas finaliser cette vente");
        }
        Vente vente1=null;
        try {
            vente1=venteService.finalizeSale(vente);
        } catch (Exception e)
        {
            return Response.send(HttpStatus.OK, "error", e.getMessage());

        }
        return Response.send(HttpStatus.OK, "success","Vente effectuee",vente1);
    }


}
