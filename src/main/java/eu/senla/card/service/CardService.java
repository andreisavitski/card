package eu.senla.card.service;

import eu.senla.card.dto.ResponseMessageDto;
import eu.senla.card.dto.TransferRequestMessageDto;

public interface CardService {

    ResponseMessageDto findCardByClientId(Long clientId);

    ResponseMessageDto makeTransfer(TransferRequestMessageDto transferRequestMessageDto);
}
