package robertovisconti.be_u5_w3_d5.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Evento {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String titolo;

    @Column(nullable = false)
    private String descrizione;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private String luogo;

    @Column(name = "posti_totali", nullable = false)
    private int postiTotali;

    @ManyToOne
    @JoinColumn(name = "organizzatore_id", nullable = false)
    private Utente organizzatore;

    public Evento(String titolo, String descrizione, LocalDate data, String luogo, int postiTotali) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.data = data;
        this.luogo = luogo;
        this.postiTotali = postiTotali;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public void setPostiTotali(int postiTotali) {
        this.postiTotali = postiTotali;
    }

    public void setOrganizzatore(Utente organizzatore) {
        this.organizzatore = organizzatore;
    }
}
