package eu.senla.card.exception;

import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public enum ApplicationError implements AppError, Supplier<ApplicationException> {

    NOT_ENOUGH_FUNDS(BAD_REQUEST, "Not enough funds on the card"),

    CARD_NOT_FOUND(NOT_FOUND, "The card does not exist"),

    CLIENT_NOT_FOUND(NOT_FOUND, "Client not found"),

    TRANSFER_NOT_FOUND(NOT_FOUND, "Transfer not found"),

    TRANSFER_NOT_COMPLETED(NOT_FOUND, "The transfer not completed. Something went wrong:("),

    TRANSFER_ALREADY_COMPLETED(BAD_REQUEST, "The transfer is already completed");

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
