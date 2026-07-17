package robertovisconti.be_u5_w3_d5.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import robertovisconti.be_u5_w3_d5.DTO.UpdateUtenteDTO;
import robertovisconti.be_u5_w3_d5.entities.Utente;
import robertovisconti.be_u5_w3_d5.exceptions.NotFoundExceptions;
import robertovisconti.be_u5_w3_d5.repositories.UtenteRepository;

import java.util.UUID;

@Service
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    public UtenteService(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Utente findById(UUID id) {
        return utenteRepository.findById(id).orElseThrow(() -> new NotFoundExceptions(id));
    }

    public Utente update(Utente utente, UpdateUtenteDTO body) {
        Utente found = this.findById(utente.getId());

        found.setEmail(body.email());
        found.setUsername(body.username());

        String passwordCriptata = passwordEncoder.encode(body.password());
        found.setPassword(passwordCriptata);

        return utenteRepository.save(found);

    }
}
