package robertovisconti.be_u5_w3_d5.exceptions;

import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import robertovisconti.be_u5_w3_d5.DTO.ErrorsDTO;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorsHandler {


    // LISTA GESTIONE ERRORI
    @ExceptionHandler(ValidationExceptions.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsDTO handleValidationExceptions(ValidationExceptions ex) {
        ex.printStackTrace();
        return new ErrorsDTO("Ci sono stati errori di validazione", LocalDateTime.now(), ex.getListaErrori());
    }


    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsDTO handleBadRequest(BadRequestException ex) {
        ex.printStackTrace();
        return new ErrorsDTO(ex.getMessage(), LocalDateTime.now());

    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorsDTO handleUnauthorized(UnauthorizedException ex) {
        return new ErrorsDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsDTO handleNotFound(NotFoundExceptions ex) {
        ex.printStackTrace();
        return new ErrorsDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsDTO handleGenericError(Exception ex) {
        ex.printStackTrace();
        return new ErrorsDTO("Errore interno del server: " + ex.getMessage(), LocalDateTime.now());
    }

}
