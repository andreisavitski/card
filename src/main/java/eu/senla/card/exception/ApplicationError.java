package eu.senla.card.exception;

import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public enum ApplicationError implements AppError, Supplier<ApplicationException> {

    CLIENT_NOT_FOUND(NOT_FOUND, "Client not found"),

    CARD_NOT_FOUND(NOT_FOUND, "Card not found"),

    THE_CARD_EXISTS(BAD_REQUEST, "A card with this number already exists");

    private final HttpStatus status;
    private final String code;

    ApplicationError(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }

    @Override
    public ApplicationException get() {
        return new ApplicationException(this);
    }

    @Override
    public final HttpStatus getStatus() {
        return status;
    }

    @Override
    public final String getCode() {
        return code;
    }
}
