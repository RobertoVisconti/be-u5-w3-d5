package robertovisconti.be_u5_w3_d5.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import robertovisconti.be_u5_w3_d5.DTO.PrenotazioneDTO;
import robertovisconti.be_u5_w3_d5.entities.Prenotazione;
import robertovisconti.be_u5_w3_d5.entities.Utente;
import robertovisconti.be_u5_w3_d5.exceptions.BadRequestException;
import robertovisconti.be_u5_w3_d5.exceptions.ValidationExceptions;
import robertovisconti.be_u5_w3_d5.services.PrenotazioneService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    public final PrenotazioneService prenotazioneService;

    public PrenotazioneController(PrenotazioneService prenotazioneService) {
        this.prenotazioneService = prenotazioneService;
    }

    // /prenotazioni/me
    @GetMapping("/me")
    public List<Prenotazione> getMePrenotazioni(@AuthenticationPrincipal Utente utente) {
        return prenotazioneService.findByUtente(utente);
    }


    // /prenotazioni
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione save(@RequestBody @Validated PrenotazioneDTO body, BindingResult validation, @AuthenticationPrincipal Utente utente) throws BadRequestException {
        if (validation.hasErrors()) {
            List<String> errori = validation.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            throw new ValidationExceptions(errori);
        }
        return prenotazioneService.save(body, utente);

    }


    // /prenotazioni/{id}
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void annullaPrenotazione(@PathVariable UUID id, @AuthenticationPrincipal Utente utente) throws BadRequestException {
        prenotazioneService.annulla(id, utente);
    }

}
