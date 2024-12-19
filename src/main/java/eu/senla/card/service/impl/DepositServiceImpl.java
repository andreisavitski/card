package eu.senla.card.service.impl;

import eu.senla.card.dto.DepositDto;
import eu.senla.card.dto.DepositOpenerDto;
import eu.senla.card.dto.DepositUpdaterDto;
import eu.senla.card.dto.ResponseMessageDto;
import eu.senla.card.entity.Card;
import eu.senla.card.entity.Client;
import eu.senla.card.entity.Deposit;
import eu.senla.card.entity.DepositType;
import eu.senla.card.mapper.DepositMapper;
import eu.senla.card.repository.CardRepository;
import eu.senla.card.repository.ClientRepository;
import eu.senla.card.repository.DepositRepository;
import eu.senla.card.repository.DepositTypeRepository;
import eu.senla.card.service.DepositService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static eu.senla.card.constant.AppConstants.ZERO;
import static eu.senla.card.util.DepositCreator.createDeposit;
import static eu.senla.card.util.ResponseMessageUtil.badRequest;
import static eu.senla.card.util.ResponseMessageUtil.ok;

@Service
@RequiredArgsConstructor
public class DepositServiceImpl implements DepositService {

    private final ClientRepository clientRepository;

    private final CardRepository cardRepository;

    private final DepositRepository depositRepository;

    private final DepositTypeRepository depositTypeRepository;

    private final DepositMapper depositMapper;

    @NotNull
    @Override
    public ResponseMessageDto openDeposit(@NotNull DepositOpenerDto depositOpenerDto) {
        final Optional<DepositType> optionalDepositType =
                depositTypeRepository.findById(depositOpenerDto.getDepositTypeId());
        final Optional<Client> optionalClient =
                clientRepository.findById(depositOpenerDto.getClientId());
        final Optional<Card> optionalCard = cardRepository.findById(depositOpenerDto.getCardId());
        if (optionalDepositType.isPresent()
                && optionalClient.isPresent()
                && optionalCard.isPresent()) {
            final Deposit deposit = createDeposit(optionalDepositType.get(), optionalCard.get(),
                    optionalClient.get());
            return saveDeposit(deposit);
        }
        return badRequest();
    }

    @NotNull
    @Override
    public ResponseMessageDto updateDeposit(@NotNull DepositUpdaterDto depositUpdaterDto) {
        final Optional<Deposit> optionalDeposit =
                depositRepository.findById(depositUpdaterDto.getDepositId());
        if (optionalDeposit.isPresent()) {
            final Deposit deposit = optionalDeposit.get();
            final BigDecimal amountAfterSum =
                    deposit.getCard().getAmount().add(depositUpdaterDto.getContributionAmount());
            if (amountAfterSum.compareTo(deposit.getDepositType().getMaxAmount()) <= ZERO) {
                deposit.getCard().setAmount(amountAfterSum);
                return saveDeposit(deposit);
            }
        }
        return badRequest();
    }

    @NotNull
    private ResponseMessageDto saveDeposit(@NotNull Deposit deposit) {
        final DepositDto depositDto;
        final List<Exception> exceptions = new ArrayList<>();
        try {
            deposit = depositRepository.save(deposit);
            depositDto = depositMapper.toDto(deposit);
        } catch (Exception e) {
            exceptions.add(e);
            return badRequest((Object) exceptions);
        }
        return ok(depositDto);
    }
}
