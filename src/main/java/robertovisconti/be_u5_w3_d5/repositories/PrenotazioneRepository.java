package robertovisconti.be_u5_w3_d5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import robertovisconti.be_u5_w3_d5.entities.Evento;
import robertovisconti.be_u5_w3_d5.entities.Prenotazione;
import robertovisconti.be_u5_w3_d5.entities.Utente;
import robertovisconti.be_u5_w3_d5.enums.StatoPrenotazione;

import java.util.List;
import java.util.UUID;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, UUID> {

    boolean existsByEventoAndStato(Evento evento, StatoPrenotazione statoPrenotazione);

    List<Prenotazione> findByUtente(Utente utente);

    long countByEventoAndStato(Evento evento, StatoPrenotazione statoPrenotazione);
}
