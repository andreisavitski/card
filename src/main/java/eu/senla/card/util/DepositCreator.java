package eu.senla.card.util;

import eu.senla.card.entity.Card;
import eu.senla.card.entity.Client;
import eu.senla.card.entity.Deposit;
import eu.senla.card.entity.DepositType;
import jakarta.validation.constraints.NotNull;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

import static java.util.UUID.randomUUID;

@UtilityClass
public class DepositCreator {

    @NotNull
    public static Deposit createDeposit(@NotNull DepositType depositType,
                                        @NotNull Card card,
                                        @NotNull Client client) {
        return Deposit.builder()
                .id(randomUUID())
                .depositOpeningDate(LocalDateTime.now())
                .depositType(depositType)
                .card(card)
                .client(client)
                .build();
    }
}