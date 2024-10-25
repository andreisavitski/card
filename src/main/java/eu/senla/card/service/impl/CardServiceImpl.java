package eu.senla.card.service.impl;

import eu.senla.card.dto.ClientCardResponse;
import eu.senla.card.dto.TransferRequestMessage;
import eu.senla.card.entity.Card;
import eu.senla.card.mapper.CardMapper;
import eu.senla.card.repository.CardRepository;
import eu.senla.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private final CardMapper cardMapper;

    @Override
    public List<ClientCardResponse> findCardByClientId(Long clientId) {
        return cardRepository.findByClientId(clientId)
                .stream()
                .map(cardMapper::toClientCardResponse)
                .toList();
    }

    @Transactional
    @Override
    public boolean makeTransfer(TransferRequestMessage transferRequestMessage) {
        Optional<Card> optionalCardFrom = cardRepository.findById(transferRequestMessage.getCardIdFrom());
        Optional<Card> optionalCardTo = cardRepository.findById(transferRequestMessage.getCardIdTo());
        if (optionalCardFrom.isPresent()
            && optionalCardTo.isPresent()
            && optionalCardFrom.get()
                       .getAmount()
                       .compareTo(transferRequestMessage.getAmount()) >= 0) {
            executeTransfer(optionalCardFrom.get(), optionalCardTo.get(), transferRequestMessage);
            return true;
        }
        return false;
    }

    private void executeTransfer(Card cardFrom, Card cardTo,
                                 TransferRequestMessage transferRequestMessage) {
        cardFrom.setAmount(cardFrom.getAmount().subtract(transferRequestMessage.getAmount()));
        cardTo.setAmount(cardTo.getAmount().add(transferRequestMessage.getAmount()));
        cardRepository.save(cardFrom);
        cardRepository.save(cardTo);
    }
}
