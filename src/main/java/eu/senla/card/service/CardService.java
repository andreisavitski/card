package eu.senla.card.service;

import eu.senla.card.dto.ClientCardResponse;
import eu.senla.card.dto.TransferRequestMessage;

import java.util.List;

public interface CardService {

    List<ClientCardResponse> findCardByClientId(Long clientId);

    boolean makeTransfer(TransferRequestMessage transferRequestMessage);
}
