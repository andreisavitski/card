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

import static eu.senla.card.converter.ResponseMessageUtil.badRequest;
import static eu.senla.card.converter.ResponseMessageUtil.ok;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private final CardMapper cardMapper;

    @NotNull
    @Override
    public ResponseMessageDto findCardByClientId(@NotNull Long clientId) {
        final List<CardDto> cards = cardRepository.findByClientId(clientId)
                .stream()
                .map(cardMapper::toClientCardResponse)
                .toList();
        return ok(cards);

    }

    @Transactional
    @NotNull
    @Override
    public ResponseMessageDto makeTransfer(@NotNull TransferRequestMessageDto transferRequestMessageDto) {
        final Optional<Card> optionalWriteOffCard =
                cardRepository.findById(transferRequestMessageDto.getWriteOffCardId());
        final Optional<Card> optionalTargetCard =
                cardRepository.findById(transferRequestMessageDto.getTopUpCardId());
        if (optionalWriteOffCard.isPresent()
                && optionalTargetCard.isPresent()
                && optionalWriteOffCard.get()
                .getAmount()
                .compareTo(transferRequestMessageDto.getAmount()) >= 0) {
            executeTransfer(optionalWriteOffCard.get(), optionalTargetCard.get(), transferRequestMessageDto);
            return ok();
        }
        return badRequest();
    }

    @NotNull
    @Override
    public ResponseMessageDto makePayment(@NotNull PaymentRequestMessageDto paymentRequestMessageDto) {
        final Optional<Card> optionalWriteOffCard =
                cardRepository.findById(paymentRequestMessageDto.getWriteOffCardId());
        if (optionalWriteOffCard.isPresent() && optionalWriteOffCard.get()
                .getAmount()
                .compareTo(paymentRequestMessageDto.getAmount()) >= 0) {
            executePayment(optionalWriteOffCard.get(), paymentRequestMessageDto);
            return ok();
        }
        return badRequest();
    }

    private void executePayment(@NotNull Card writeOffCard,
                                @NotNull PaymentRequestMessageDto paymentRequestMessageDto) {
        writeOffCard.setAmount(writeOffCard.getAmount().subtract(paymentRequestMessageDto.getAmount()));
        cardRepository.save(writeOffCard);
    }

    private void executeTransfer(@NotNull Card writeOffCard, @NotNull Card targetCard,
                                 @NotNull TransferRequestMessageDto transferRequestMessageDto) {
        writeOffCard.setAmount(writeOffCard.getAmount().subtract(transferRequestMessageDto.getAmount()));
        targetCard.setAmount(targetCard.getAmount().add(transferRequestMessageDto.getAmount()));
        cardRepository.save(writeOffCard);
        cardRepository.save(targetCard);
    }
}
