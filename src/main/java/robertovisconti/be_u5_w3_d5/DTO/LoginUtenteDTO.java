package robertovisconti.be_u5_w3_d5.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginUtenteDTO {

    @NotBlank(message = "Lo username è obbligatorio")
    private String username;

    @NotBlank(message = "La password è obbligatoria")
    private String password;
}
