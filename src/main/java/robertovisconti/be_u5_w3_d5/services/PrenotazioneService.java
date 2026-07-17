package robertovisconti.be_u5_w3_d5.services;

import org.springframework.stereotype.Service;
import robertovisconti.be_u5_w3_d5.DTO.PrenotazioneDTO;
import robertovisconti.be_u5_w3_d5.entities.Evento;
import robertovisconti.be_u5_w3_d5.entities.Prenotazione;
import robertovisconti.be_u5_w3_d5.entities.Utente;
import robertovisconti.be_u5_w3_d5.enums.StatoPrenotazione;
import robertovisconti.be_u5_w3_d5.exceptions.BadRequestException;
import robertovisconti.be_u5_w3_d5.exceptions.NotFoundExceptions;
import robertovisconti.be_u5_w3_d5.exceptions.UnauthorizedException;
import robertovisconti.be_u5_w3_d5.repositories.EventoRepository;
import robertovisconti.be_u5_w3_d5.repositories.PrenotazioneRepository;

import java.util.List;
import java.util.UUID;

@Service
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;
    private final EventoRepository eventoRepository;

    public PrenotazioneService(PrenotazioneRepository prenotazioneRepository, EventoRepository eventoRepository) {
        this.prenotazioneRepository = prenotazioneRepository;
        this.eventoRepository = eventoRepository;
    }

    public List<Prenotazione> findByUtente(Utente utente) {
        return prenotazioneRepository.findByUtente(utente);
    }

    public Prenotazione save(PrenotazioneDTO body, Utente utente) {
        if (!utente.getRuolo().name().equals("UTENTE")) {
            throw new UnauthorizedException("Solo gli utenti normali possono effettuare prenotazioni");
        }

        Evento evento = eventoRepository.findById(body.eventoId()).orElseThrow(() -> new NotFoundExceptions("Evento con ID: " + body.eventoId() + " non trovato"));

        long prenotazioniAttive = prenotazioneRepository.countByEventoAndStatoPrenotazione(evento, StatoPrenotazione.ATTIVA);

        if (prenotazioniAttive >= evento.getPostiTotali()) {
            throw new BadRequestException(" I posti per questo evento sono esauriti");
        }

        Prenotazione nuovaPrenotazione = new Prenotazione();
        nuovaPrenotazione.setEvento(evento);
        nuovaPrenotazione.setUtente(utente);

        return prenotazioneRepository.save(nuovaPrenotazione);

    }

    public void annulla(UUID id, Utente utente) throws BadRequestException {
        Prenotazione found = prenotazioneRepository.findById(id).orElseThrow(() -> new NotFoundExceptions(id));
        if (!found.getUtente().getId().equals(utente.getId())) {
            throw new UnauthorizedException("Non sei autorizzato ad annullare questa prenotazione");
        }
        if (found.getStatoPrenotazione() == StatoPrenotazione.ANNULLATA) {
            throw new BadRequestException("Questa prenotazione è già annullata");
        }
        found.setStatoPrenotazione(StatoPrenotazione.ANNULLATA);

        prenotazioneRepository.save(found);
    }
}
