package eu.senla.card.service.impl;

import eu.senla.card.dto.CardRequestDto;
import eu.senla.card.dto.CardResponseDto;
import eu.senla.card.dto.ClientCardResponse;
import eu.senla.card.dto.TransferRequestMessage;
import eu.senla.card.entity.Card;
import eu.senla.card.entity.Client;
import eu.senla.card.exception.ApplicationException;
import eu.senla.card.mapper.CardMapper;
import eu.senla.card.repository.CardRepository;
import eu.senla.card.repository.ClientRepository;
import eu.senla.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static eu.senla.card.exception.ApplicationError.*;

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

    @Override
    public CardResponseDto getCardById(Long id) {
        return cardMapper.toDto(cardRepository.findById(id).orElseThrow(
                () -> new ApplicationException(CARD_NOT_FOUND))
        );
    }

    @Transactional
    @Override
    public boolean makeTransfer(TransferRequestMessage transferRequestMessage) {
        Optional<Card> cardFrom = cardRepository.findById(transferRequestMessage.getCardIdFrom());
        Optional<Card> cardTo = cardRepository.findById(transferRequestMessage.getCardIdTo());
        if (cardFrom.isPresent()
            && cardTo.isPresent()
            && cardFrom.get()
                       .getAmount()
                       .compareTo(transferRequestMessage.getAmount()) < 0) {
            return false;
        }
        cardFrom.orElseThrow().setAmount(cardFrom.get().getAmount()
                .subtract(transferRequestMessage.getAmount()));
        cardTo.orElseThrow().setAmount(cardTo.get().getAmount()
                .add(transferRequestMessage.getAmount()));
        cardRepository.save(cardFrom.get());
        cardRepository.save(cardTo.get());
        return true;
    }

    private CardRequestDto checkingUniqueNumber(CardRequestDto cardRequestDto) {
        if (cardRepository.findByNumber(cardRequestDto.getNumber()).isEmpty()) {
            return cardRequestDto;
        } else {
            throw new ApplicationException(THE_CARD_EXISTS);
        }
    }
}
