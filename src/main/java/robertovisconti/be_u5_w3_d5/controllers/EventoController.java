package robertovisconti.be_u5_w3_d5.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import robertovisconti.be_u5_w3_d5.DTO.EventoDTO;
import robertovisconti.be_u5_w3_d5.entities.Evento;
import robertovisconti.be_u5_w3_d5.entities.Utente;
import robertovisconti.be_u5_w3_d5.exceptions.BadRequestException;
import robertovisconti.be_u5_w3_d5.exceptions.ValidationExceptions;
import robertovisconti.be_u5_w3_d5.services.EventoService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/eventi")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public List<Evento> getEventi() {
        return eventoService.findAll();
    }

    // GET /eventi/{id}
    @GetMapping("/{id}")
    public Evento getEventoId(@PathVariable UUID id) {
        return eventoService.findById(id);
    }

    // POST /eventi (solo organizzatori)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Evento creaEvento(@RequestBody @Validated EventoDTO body, BindingResult validation, @AuthenticationPrincipal Utente utente) {
        if (validation.hasErrors()) {
            List<String> errori = validation.getAllErrors().stream()
                    .map(errore -> errore.getDefaultMessage())
                    .collect(Collectors.toList());
            throw new ValidationExceptions(errori);
        }
        return eventoService.save(body, utente);
    }

    // PUT /eventi/{id} (solo organizzatori proprietario dell'evento)
    @PutMapping("/{id}")
    public Evento updateEvento(@PathVariable UUID id, @RequestBody @Validated EventoDTO body, BindingResult validation, @AuthenticationPrincipal Utente utente) {
        if (validation.hasErrors()) {
            List<String> errori = validation.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            throw new ValidationExceptions(errori);
        }
        return eventoService.update(id, body, utente);
    }

    // DELETE /eventi/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvento(@PathVariable UUID id, @AuthenticationPrincipal Utente utente) throws BadRequestException {
        eventoService.delete(id, utente);
    }
}
