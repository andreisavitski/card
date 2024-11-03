package eu.senla.card.service.impl;

import eu.senla.card.dto.ClientCardResponseDto;
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

    @Override
    public ResponseMessageDto findCardByClientId(@NotNull Long clientId) {
        final List<ClientCardResponseDto> cards = cardRepository.findByClientId(clientId)
                .stream()
                .map(cardMapper::toClientCardResponse)
                .toList();
        return ok(cards);

    }

    @Transactional
    @Override
    @NotNull
    public ResponseMessageDto makeTransfer(@NotNull TransferRequestMessageDto transferRequestMessageDto) {
        final Optional<Card> optionalCardFrom = cardRepository.findById(transferRequestMessageDto.getCardIdFrom());
        final Optional<Card> optionalCardTo = cardRepository.findById(transferRequestMessageDto.getCardIdTo());
        if (optionalCardFrom.isPresent()
                && optionalCardTo.isPresent()
                && optionalCardFrom.get()
                .getAmount()
                .compareTo(transferRequestMessageDto.getAmount()) >= 0) {
            executeTransfer(optionalCardFrom.get(), optionalCardTo.get(), transferRequestMessageDto);
            return ok();
        }
        return badRequest();
    }

    private void executeTransfer(@NotNull Card cardFrom, @NotNull Card cardTo,
                                 @NotNull TransferRequestMessageDto transferRequestMessageDto) {
        cardFrom.setAmount(cardFrom.getAmount().subtract(transferRequestMessageDto.getAmount()));
        cardTo.setAmount(cardTo.getAmount().add(transferRequestMessageDto.getAmount()));
        cardRepository.save(cardFrom);
        cardRepository.save(cardTo);
    }
}
