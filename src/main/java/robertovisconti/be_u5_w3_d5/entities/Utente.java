package robertovisconti.be_u5_w3_d5.entities;

import jakarta.persistence.*;
import lombok.Getter;
import robertovisconti.be_u5_w3_d5.enums.Ruolo;

import java.util.UUID;

@Entity
@Getter
public class Utente {

    @Id
    @GeneratedValue
    @Column(name = "id_utente")
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;


    public Utente(String nome, String cognome, String username, String email, String password, Ruolo ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.email = email;
        this.password = password;
        this.ruolo = Ruolo.UTENTE;
    }
}
