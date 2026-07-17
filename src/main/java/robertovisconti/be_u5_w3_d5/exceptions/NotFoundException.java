package robertovisconti.be_u5_w3_d5.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("Utente non trovato con ID: " + id);
    }

    public NotFoundException(String message) {
        super(message);
    }


}
