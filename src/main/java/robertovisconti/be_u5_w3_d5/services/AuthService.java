package robertovisconti.be_u5_w3_d5.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import robertovisconti.be_u5_w3_d5.DTO.LoginUtenteDTO;
import robertovisconti.be_u5_w3_d5.DTO.RegistrazioneUtenteDTO;
import robertovisconti.be_u5_w3_d5.entities.Utente;
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
        if (utenteRepository.existsByEmail(body.getEmail())) {
            throw new IllegalArgumentException("Email già in uso");
        }
        if (utenteRepository.existsByUsername(body.getUsername())) {
            throw new IllegalArgumentException("Username già in uso");
        }
        Utente utente = new Utente();
        utente.setNome(body.getNome());
        utente.setCognome(body.getCognome());
        utente.setUsername(body.getUsername());
        utente.setEmail(body.getEmail());

        String passwordCriptata = passwordEncoder.encode(body.getPassword());
        utente.setPassword(passwordCriptata);

        return utenteRepository.save(utente);

    }

    public String autenticaUtenteEGeneraToken(LoginUtenteDTO body) {
        Utente utente = utenteRepository.findByUsername(body.getUsername()).orElseThrow(() -> new UnauthorizedException("Credenziali non valide"));
        if (!passwordEncoder.matches(body.getPassword(), utente.getPassword())) {
            throw new UnauthorizedException("Credenziali non valide");
        }

        return jwtTools.generateToken(utente);
    }
}
