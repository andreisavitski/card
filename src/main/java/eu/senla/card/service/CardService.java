package eu.senla.card.service;

import eu.senla.card.dto.CardRequestDto;
import eu.senla.card.dto.CardResponseDto;
import eu.senla.card.dto.ClientCardResponse;

import java.util.List;

public interface CardService {

    CardResponseDto addCard(CardRequestDto cardRequestDto);

    List<ClientCardResponse> findCardByClientId(Long clientId);
}
