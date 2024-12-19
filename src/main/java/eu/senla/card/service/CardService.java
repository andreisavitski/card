package eu.senla.card.service;

import eu.senla.card.dto.PaymentRequestMessageDto;
import eu.senla.card.dto.ResponseMessageDto;
import eu.senla.card.dto.TransferRequestMessageDto;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public interface CardService {

    @NotNull
    ResponseMessageDto findCardByClientId(@NotNull UUID clientId);

    @NotNull
    ResponseMessageDto makeTransfer(@NotNull TransferRequestMessageDto transferRequestMessageDto);

    @NotNull
    ResponseMessageDto makePayment(@NotNull PaymentRequestMessageDto paymentRequestMessageDto);

    @NotNull
    ResponseMessageDto addCard(@NotNull UUID clientId);
}
