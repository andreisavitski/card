package eu.senla.card.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Data
@Builder
@Jacksonized
public class PaymentRequestMessageDto {

    private final Long writeOffCardId;

    private final Long clientId;

    private final BigDecimal amount;
}
