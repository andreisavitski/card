package eu.senla.card.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@Jacksonized
public class PaymentRequestMessageDto {

    private final UUID writeOffCardId;

    private final UUID clientId;

    private final BigDecimal amount;
}
