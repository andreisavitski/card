package eu.senla.card.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Data
@Builder
@Jacksonized
public class TransferRequestMessage {

    private Long cardIdFrom;

    private Long cardIdTo;

    private Long clientId;

    private BigDecimal amount;
}
