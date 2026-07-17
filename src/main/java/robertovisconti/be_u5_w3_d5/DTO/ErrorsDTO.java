package robertovisconti.be_u5_w3_d5.DTO;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorsDTO(String message, LocalDateTime timestamp, List<String> listaErrori) {
    public ErrorsDTO(String message, LocalDateTime timestamp) {
        this(message, timestamp, null);
    }
}
