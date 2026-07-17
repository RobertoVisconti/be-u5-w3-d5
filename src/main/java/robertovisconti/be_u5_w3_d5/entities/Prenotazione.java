package robertovisconti.be_u5_w3_d5.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import robertovisconti.be_u5_w3_d5.enums.StatoPrenotazione;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Prenotazione {

    @Id
    @GeneratedValue
    @Column(name = "id_prenotazione")
    private UUID id;

    @Column(name = "data_prenotazione", nullable = false)
    private LocalDateTime dataPrenotazione = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private StatoPrenotazione statoPrenotazione;

    @ManyToOne
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "id_utente", nullable = false)
    private Utente utente;

    public Prenotazione(LocalDateTime dataPrenotazione, StatoPrenotazione statoPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
        this.statoPrenotazione = StatoPrenotazione.ATTIVA;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public void setStatoPrenotazione(StatoPrenotazione statoPrenotazione) {
        this.statoPrenotazione = statoPrenotazione;
    }
}
