package robertovisconti.be_u5_w3_d5.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import robertovisconti.be_u5_w3_d5.DTO.LoginUtenteDTO;
import robertovisconti.be_u5_w3_d5.DTO.RegistrazioneUtenteDTO;
import robertovisconti.be_u5_w3_d5.entities.Utente;
import robertovisconti.be_u5_w3_d5.exceptions.BadRequestException;
import robertovisconti.be_u5_w3_d5.exceptions.UnauthorizedException;
import robertovisconti.be_u5_w3_d5.repositories.UtenteRepository;
import robertovisconti.be_u5_w3_d5.security.JWTTools;

@Service
public class AuthService {
    private final JWTTools jwtTools;
    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UtenteRepository utenteRepository, JWTTools jwtTools, PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.jwtTools = jwtTools;
        this.passwordEncoder = passwordEncoder;
    }

    public Utente registraUtente(RegistrazioneUtenteDTO body) {
        if (utenteRepository.existsByEmail(body.email())) {
            throw new BadRequestException("Email già in uso");
        }
        if (utenteRepository.existsByUsername(body.username())) {
            throw new BadRequestException("Username già in uso");
        }
        Utente utente = new Utente();
        utente.setNome(body.nome());
        utente.setCognome(body.cognome());
        utente.setUsername(body.username());
        utente.setEmail(body.email());

        String passwordCriptata = passwordEncoder.encode(body.password());
        utente.setPassword(passwordCriptata);

        return utenteRepository.save(utente);

    }

    public String autenticaUtenteEGeneraToken(LoginUtenteDTO body) {
        Utente utente = utenteRepository.findByUsername(body.username()).orElseThrow(() -> new UnauthorizedException("Credenziali non valide"));
        if (!passwordEncoder.matches(body.password(), utente.getPassword())) {
            throw new UnauthorizedException("Credenziali non valide");
        }

        return jwtTools.generateToken(utente);
    }
}
