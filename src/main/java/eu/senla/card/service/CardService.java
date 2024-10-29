package eu.senla.card.service;

import eu.senla.card.dto.ClientCardResponse;
import eu.senla.card.dto.TransferRequestMessage;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface CardService {

    List<ClientCardResponse> findCardByClientId(Long clientId);

    HttpStatus makeTransfer(TransferRequestMessage transferRequestMessage);
}
