package eu.senla.card.service;

import eu.senla.card.dto.ResponseMessage;
import eu.senla.card.dto.TransferRequestMessage;

public interface CardService {

    ResponseMessage findCardByClientId(Long clientId);

    ResponseMessage makeTransfer(TransferRequestMessage transferRequestMessage);
}
