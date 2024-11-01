package eu.senla.card.service.impl;

import eu.senla.card.dto.ClientCardResponse;
import eu.senla.card.dto.ResponseMessage;
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

import static eu.senla.card.converter.ResponseMessageUtil.badRequest;
import static eu.senla.card.converter.ResponseMessageUtil.ok;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private final CardMapper cardMapper;

    @Override
    public ResponseMessage findCardByClientId(Long clientId) {
        List<ClientCardResponse> cards = cardRepository.findByClientId(clientId)
                .stream()
                .map(cardMapper::toClientCardResponse)
                .toList();
        return ok(cards);

    }

    @Transactional
    @Override
    public ResponseMessage makeTransfer(TransferRequestMessage transferRequestMessage) {
        Optional<Card> optionalCardFrom = cardRepository.findById(transferRequestMessage.getCardIdFrom());
        Optional<Card> optionalCardTo = cardRepository.findById(transferRequestMessage.getCardIdTo());
        if (optionalCardFrom.isPresent()
                && optionalCardTo.isPresent()
                && optionalCardFrom.get()
                .getAmount()
                .compareTo(transferRequestMessage.getAmount()) >= 0) {
            executeTransfer(optionalCardFrom.get(), optionalCardTo.get(), transferRequestMessage);
            return ok();
        }
        return badRequest();
    }

    private void executeTransfer(Card cardFrom, Card cardTo,
                                 TransferRequestMessage transferRequestMessage) {
        cardFrom.setAmount(cardFrom.getAmount().subtract(transferRequestMessage.getAmount()));
        cardTo.setAmount(cardTo.getAmount().add(transferRequestMessage.getAmount()));
        cardRepository.save(cardFrom);
        cardRepository.save(cardTo);
    }
}
