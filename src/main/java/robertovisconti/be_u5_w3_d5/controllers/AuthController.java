package robertovisconti.be_u5_w3_d5.controllers;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import robertovisconti.be_u5_w3_d5.DTO.LoginUtenteDTO;
import robertovisconti.be_u5_w3_d5.DTO.RegistrazioneUtenteDTO;
import robertovisconti.be_u5_w3_d5.entities.Utente;
import robertovisconti.be_u5_w3_d5.services.AuthService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ENDPOINT REGISTRAZIONE
    @PostMapping("/registrazione")
    @ResponseStatus(HttpStatus.CREATED)
    public Utente registrazioneUtente(@RequestBody @Valid RegistrazioneUtenteDTO body) {
        return this.authService.registraUtente(body);
    }

    // ENDPOINT LOGIN
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody @Valid LoginUtenteDTO body) {
        String token = this.authService.autenticaUtenteEGeneraToken(body);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }
}
