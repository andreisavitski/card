package eu.senla.card.service.impl;

import eu.senla.card.dto.CardRequestDto;
import eu.senla.card.dto.CardResponseDto;
import eu.senla.card.dto.ClientCardResponse;
import eu.senla.card.entity.Card;
import eu.senla.card.entity.Client;
import eu.senla.card.exception.ApplicationException;
import eu.senla.card.mapper.CardMapper;
import eu.senla.card.repository.CardRepository;
import eu.senla.card.repository.ClientRepository;
import eu.senla.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static eu.senla.card.exception.ApplicationError.CLIENT_NOT_FOUND;
import static eu.senla.card.exception.ApplicationError.THE_CARD_EXISTS;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private final ClientRepository clientRepository;

    private final CardMapper cardMapper;

    @Override
    public CardResponseDto addCard(CardRequestDto cardRequestDto) {
        Card card = cardMapper.toCard(checkingUniqueNumber(cardRequestDto));
        Client client = clientRepository.findById(card.getClient().getId()).orElseThrow(
                () -> new ApplicationException(CLIENT_NOT_FOUND)
        );
        card.setClient(client);
        return cardMapper.toDto(cardRepository.save(card));
    }

    @Override
    public List<ClientCardResponse> findCardByClientId(Long clientId) {
        return cardRepository.findByClientId(clientId)
                .stream()
                .map(cardMapper::toClientCardResponse)
                .toList();
    }


    private CardRequestDto checkingUniqueNumber(CardRequestDto cardRequestDto) {
        if (cardRepository.findByNumber(cardRequestDto.getNumber()).isEmpty()) {
            return cardRequestDto;
        } else {
            throw new ApplicationException(THE_CARD_EXISTS);
        }
    }
}
