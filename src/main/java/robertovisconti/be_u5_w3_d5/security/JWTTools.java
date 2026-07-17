package robertovisconti.be_u5_w3_d5.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import robertovisconti.be_u5_w3_d5.entities.Utente;
import robertovisconti.be_u5_w3_d5.exceptions.UnauthorizedException;

import java.util.Date;

@Component
public class JWTTools {

    private final String secret;

    public JWTTools(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }


    public String generateToken(Utente utente) {
        // il builder ci permette di costruire il token, metodo della libreria
        return Jwts.builder()
                // Data Emissione
                .issuedAt(new Date(System.currentTimeMillis()))
                // Data Scadenza
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                // Proprietario del token / id dell'utente
                .subject(String.valueOf(utente.getId()))
                // Firma token per l integrità
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public void verifyToken(String token) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        } catch (Exception ex) {
            throw new UnauthorizedException("Ci sono stati problemi con il token! Rieffettuare login!");
        }

    }

    public String extractIdFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (Exception ex) {
            throw new UnauthorizedException("Impossibile decifrare il token!");
        }
    }


}
