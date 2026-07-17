package robertovisconti.be_u5_w3_d5.controllers;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import robertovisconti.be_u5_w3_d5.DTO.UpdateUtenteDTO;
import robertovisconti.be_u5_w3_d5.entities.Utente;
import robertovisconti.be_u5_w3_d5.exceptions.ValidationExceptions;
import robertovisconti.be_u5_w3_d5.services.UtenteService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/utenti")
public class UtenteController {

    private final UtenteService utenteService;

    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @GetMapping("/me")
    public Utente getProfilo(@AuthenticationPrincipal Utente utente) {
        return utente;
    }

    @PutMapping("/me")
    public Utente update(@AuthenticationPrincipal Utente utente, @RequestBody @Validated UpdateUtenteDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errori = validation.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            throw new ValidationExceptions(errori);
        }
        return utenteService.update(utente, body);
    }

}
