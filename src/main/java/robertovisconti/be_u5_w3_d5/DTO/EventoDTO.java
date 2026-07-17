package robertovisconti.be_u5_w3_d5.DTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EventoDTO(
        @NotBlank(message = "Il titolo è obbligatorio")
        String titolo,

        @NotBlank(message = "La descrizione è obbligatoria")
        String descrizione,

        @NotNull(message = "La data dell'evento è obbligatoria")
        @Future(message = "La data deve essere nel futuro")
        LocalDate data,

        @NotBlank(message = "Il luogo dell'evento è obbligatorio")
        String luogo,

        @NotNull(message = "Il numero di posti totali è obbligatorio")
        @Min(value = 1, message = "Ci deve essere almeno un posto disponibile")
        int postiTotali
) {
}
