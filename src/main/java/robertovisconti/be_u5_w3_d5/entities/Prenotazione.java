package robertovisconti.be_u5_w3_d5.entities;


import jakarta.persistence.*;
import lombok.Getter;
import robertovisconti.be_u5_w3_d5.enums.StatoPrenotazione;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
public class Prenotazione {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "data_prenotazione", nullable = false)
    private LocalDateTime dataPrenotazione;

    @Enumerated(EnumType.STRING)
    private StatoPrenotazione statoPrenotazione;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Evento evento;

    public Prenotazione(LocalDateTime dataPrenotazione, StatoPrenotazione statoPrenotazione) {
        this.dataPrenotazione = LocalDateTime.now();
        this.statoPrenotazione = StatoPrenotazione.ATTIVA;
    }
}
