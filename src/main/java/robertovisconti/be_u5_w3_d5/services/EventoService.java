package robertovisconti.be_u5_w3_d5.services;

import org.springframework.stereotype.Service;
import robertovisconti.be_u5_w3_d5.DTO.EventoDTO;
import robertovisconti.be_u5_w3_d5.entities.Evento;
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
public class EventoService {

    private final EventoRepository eventoRepository;
    private final PrenotazioneRepository prenotazioneRepository;

    public EventoService(EventoRepository eventoRepository, PrenotazioneRepository prenotazioneRepository) {
        this.eventoRepository = eventoRepository;
        this.prenotazioneRepository = prenotazioneRepository;
    }


    public Evento save(EventoDTO body, Utente utente) {
        if (!utente.getRuolo().name().equals("ORGANIZZATORE_EVENTO")) {
            throw new UnauthorizedException("Solo gli organizzatori possono creare eventi!");
        }

        Evento nuovoEvento = new Evento();
        nuovoEvento.setTitolo(body.titolo());
        nuovoEvento.setDescrizione(body.descrizione());
        nuovoEvento.setData(body.data());
        nuovoEvento.setLuogo(body.luogo());
        nuovoEvento.setPostiTotali(body.postiTotali());
        nuovoEvento.setOrganizzatore(utente);

        return eventoRepository.save(nuovoEvento);
    }


    public List<Evento> findAll() {
        return eventoRepository.findAll();
    }

    public Evento findById(UUID id) {
        return eventoRepository.findById(id).orElseThrow(() -> new NotFoundExceptions(id));
    }

    public Evento update(UUID id, EventoDTO body, Utente utente) {
        Evento found = this.findById(id);

        if (!found.getOrganizzatore().getId().equals(utente.getId())) {
            throw new UnauthorizedException("Non dei autorizzato a modificare questo evento!");
        }

        found.setTitolo(body.titolo());
        found.setDescrizione(body.descrizione());
        found.setData(body.data());
        found.setLuogo(body.luogo());
        found.setPostiTotali(body.postiTotali());

        return eventoRepository.save(found);
    }

    public void delete(UUID id, Utente utente) {
        Evento found = this.findById(id);
        if (!found.getOrganizzatore().getId().equals(utente.getId())) {
            throw new UnauthorizedException("Non sei autorizzato a eliminare questo evento!");
        }

        boolean prenotazioniAttive = prenotazioneRepository.existsByEventoAndStato(found, StatoPrenotazione.ATTIVA);

        if (prenotazioniAttive) {
            throw new BadRequestException("Impossibile eliminare l'evento: ci sono ancora prenotazioni attive");
        }

        eventoRepository.delete(found);
    }


}
