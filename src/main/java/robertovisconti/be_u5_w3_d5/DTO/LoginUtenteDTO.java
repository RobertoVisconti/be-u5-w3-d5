package robertovisconti.be_u5_w3_d5.DTO;

import jakarta.validation.constraints.NotBlank;


public record LoginUtenteDTO(
        @NotBlank(message = "Lo username è obbligatorio")
        String username,

        @NotBlank(message = "La password è obbligatoria")
        String password
) {
}
