package eu.senla.card.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Data
@Builder
@Jacksonized
public class TransferRequestMessageDto {

    private final Long cardIdFrom;

    private final Long cardIdTo;

    private final Long clientId;

    private final BigDecimal amount;
}
