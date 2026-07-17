package robertovisconti.be_u5_w3_d5.services;

import org.springframework.stereotype.Service;
import robertovisconti.be_u5_w3_d5.entities.Utente;
import robertovisconti.be_u5_w3_d5.exceptions.NotFoundExceptions;
import robertovisconti.be_u5_w3_d5.repositories.UtenteRepository;

import java.util.UUID;

@Service
public class UtenteService {

    private final UtenteRepository utenteRepository;

    public UtenteService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    public Utente findById(UUID id) {
        return utenteRepository.findById(id).orElseThrow(() -> new NotFoundExceptions(id));
    }
}
