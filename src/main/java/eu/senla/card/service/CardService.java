package eu.senla.card.service;

import eu.senla.card.dto.PaymentRequestMessageDto;
import eu.senla.card.dto.ResponseMessageDtoTest;
import eu.senla.card.dto.TransferRequestMessageDto;
import jakarta.validation.constraints.NotNull;

public interface CardService {

    @NotNull
    ResponseMessageDtoTest findCardByClientId(@NotNull Long clientId);

    @NotNull
    ResponseMessageDtoTest makeTransfer(@NotNull TransferRequestMessageDto transferRequestMessageDto);

    @NotNull
    ResponseMessageDtoTest makePayment(@NotNull PaymentRequestMessageDto paymentRequestMessageDto);
}
