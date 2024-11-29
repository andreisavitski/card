package eu.senla.card.service.impl;

import eu.senla.card.dto.CardDto;
import eu.senla.card.dto.PaymentRequestMessageDto;
import eu.senla.card.dto.ResponseMessageDto;
import eu.senla.card.dto.TransferRequestMessageDto;
import eu.senla.card.entity.Card;
import eu.senla.card.mapper.CardMapper;
import eu.senla.card.repository.CardRepository;
import eu.senla.card.service.CardService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static eu.senla.card.util.ResponseMessageUtil.badRequest;
import static eu.senla.card.util.ResponseMessageUtil.ok;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private final CardMapper cardMapper;

    @NotNull
    @Override
    public ResponseMessageDto findCardByClientId(@NotNull Long clientId) {
        final List<CardDto> cards = cardRepository.findByClientId(clientId).stream()
                .map(cardMapper::toClientCardResponse)
                .toList();
        return ok(cards);

    }

    @Transactional
    @NotNull
    @Override
    public ResponseMessageDto makeTransfer(@NotNull TransferRequestMessageDto messageDto) {
        final Optional<Card> optionalWriteOffCard =
                cardRepository.findById(messageDto.getWriteOffCardId());
        final Optional<Card> optionalTargetCard =
                cardRepository.findById(messageDto.getTopUpCardId());
        if (optionalWriteOffCard.isPresent()
                && optionalTargetCard.isPresent()
                && optionalWriteOffCard.get()
                .getAmount()
                .compareTo(messageDto.getAmount()) >= 0) {
            executeTransfer(optionalWriteOffCard.get(), optionalTargetCard.get(), messageDto);
            return ok();
        }
        return badRequest();
    }

    @NotNull
    @Override
    public ResponseMessageDto makePayment(@NotNull PaymentRequestMessageDto messageDto) {
        final Optional<Card> optionalWriteOffCard =
                cardRepository.findById(messageDto.getWriteOffCardId());
        if (optionalWriteOffCard.isPresent() && optionalWriteOffCard.get()
                .getAmount()
                .compareTo(messageDto.getAmount()) >= 0) {
            executePayment(optionalWriteOffCard.get(), messageDto);
            return ok();
        }
        return badRequest();
    }

    private void executePayment(@NotNull Card writeOffCard,
                                @NotNull PaymentRequestMessageDto paymentRequestMessageDto) {
        writeOffCard.setAmount(writeOffCard.getAmount().subtract(
                paymentRequestMessageDto.getAmount()));
        cardRepository.save(writeOffCard);
    }

    private void executeTransfer(@NotNull Card writeOffCard, @NotNull Card targetCard,
                                 @NotNull TransferRequestMessageDto messageDto) {
        writeOffCard.setAmount(writeOffCard.getAmount().subtract(messageDto.getAmount()));
        targetCard.setAmount(targetCard.getAmount().add(messageDto.getAmount()));
        cardRepository.save(writeOffCard);
        cardRepository.save(targetCard);
    }
}
