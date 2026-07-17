package robertovisconti.be_u5_w3_d5.exceptions;

import java.util.UUID;

public class NotFoundExceptions extends RuntimeException {
    public NotFoundExceptions(UUID id) {
        super("Utente non trovato con ID: " + id);
    }
}
