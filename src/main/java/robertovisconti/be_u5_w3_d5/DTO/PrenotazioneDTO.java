package robertovisconti.be_u5_w3_d5.DTO;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PrenotazioneDTO(
        @NotNull(message = "L'id dell'evento è obbligatorio")
        UUID eventoId
) {
}
