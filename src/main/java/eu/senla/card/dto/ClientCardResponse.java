package eu.senla.card.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Data
@Builder
@Jacksonized
public class ClientCardResponse {

    private Long id;

    private Long number;

    private BigDecimal amount;

    private Long clientId;
}
