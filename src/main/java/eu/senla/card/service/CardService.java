package eu.senla.card.service;

import eu.senla.card.dto.CardRequestDto;
import eu.senla.card.dto.CardResponseDto;
import eu.senla.card.dto.ClientCardResponse;
import eu.senla.card.dto.TransferRequestMessage;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

public interface CardService {

    CardResponseDto addCard(CardRequestDto cardRequestDto);

    List<ClientCardResponse> findCardByClientId(Long clientId);

    CardResponseDto getCardById(Long id);

    boolean makeTransfer(TransferRequestMessage transferRequestMessage) throws IOException, InterruptedException, TimeoutException;
}
