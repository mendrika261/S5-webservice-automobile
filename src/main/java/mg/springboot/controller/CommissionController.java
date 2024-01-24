package mg.springboot.controller;

import jakarta.validation.Valid;
import mg.springboot.entity.Commission;
import mg.springboot.security.Response;
import mg.springboot.service.CommissionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class CommissionController {
    private final CommissionService commissionService;

    public CommissionController(CommissionService commissionService) {
        this.commissionService = commissionService;
    }

    @GetMapping("/commission_historiques")
    public Response<?> getHistorique()
    {
        return Response.send(HttpStatus.OK, "success", commissionService.getHistorique());
    }

    @GetMapping("/commissions")
    public Response<?> getCommissions()
    {
        return Response.send(HttpStatus.OK, "success", commissionService.getCommissions(LocalDateTime.now()));
    }

    @GetMapping("/commissions/{id}")
    public Response<?> getCommission(@PathVariable Integer id)
    {
        return Response.send(HttpStatus.OK, "success", commissionService.findById(id));
    }

    @PostMapping("/commissions")
    public Response<?> changeCommission(@Valid Commission commission)
    {
        return Response.send(HttpStatus.OK, "success", commissionService.save(commission));
    }

    @DeleteMapping("/commissions")
    public Response<?> deleteCommission(Integer id)
    {
        return Response.send(HttpStatus.OK, "success", commissionService.delete(id));
    }
}
