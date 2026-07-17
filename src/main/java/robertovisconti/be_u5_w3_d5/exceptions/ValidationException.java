package robertovisconti.be_u5_w3_d5.exceptions;

import java.util.List;

public class ValidationException extends RuntimeException {

    private final List<String> listaErrori;

    public ValidationException(List<String> listaErrori) {
        super("Ci sono errori di validazione");
        this.listaErrori = listaErrori;
    }

    public List<String> getListaErrori() {
        return this.listaErrori;
    }
}
