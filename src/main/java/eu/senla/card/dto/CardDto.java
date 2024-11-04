package eu.senla.card.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Data
@Builder
@Jacksonized
public class CardDto {

    private final Long id;

    private final Long number;

    private final BigDecimal amount;

    private final Long clientId;
}
