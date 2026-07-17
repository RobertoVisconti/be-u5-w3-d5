package robertovisconti.be_u5_w3_d5.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegistrazioneUtenteDTO {

    @NotBlank(message = "Il nome è obbligatorio")
    private String nome;

    @NotBlank(message = "Il cognome è obbligatorio")
    private String cognome;

    @NotBlank(message = "Lo username è obbligatorio")
    @Size(min = 4, max = 20, message = "Lo username deve essere tra i 4 e i 20 caratteri")
    private String username;

    @Email(message = "Inserisci un indirizzo email valido")
    @NotBlank(message = "L'email è obbligatoria")
    private String email;

    @NotBlank(message = "La password è obbligatoria")
    @Size(min = 6, message = "La password deve avere almeno 6 caratteri")
    private String password;
    
}
