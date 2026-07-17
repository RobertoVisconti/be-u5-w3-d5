package robertovisconti.be_u5_w3_d5.services;

import org.springframework.stereotype.Service;
import robertovisconti.be_u5_w3_d5.security.JWTTools;

@Service
public class AuthService {
    private final JWTTools jwtTools;
    private final UtenteService utenteService;

    public AuthService(UtenteService utenteService, JWTTools jwtTools) {
        this.utenteService = utenteService;
        this.jwtTools = jwtTools;
    }
}
