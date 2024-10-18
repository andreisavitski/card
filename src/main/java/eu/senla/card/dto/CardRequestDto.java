package eu.senla.card.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Data
@Builder
@Jacksonized
public class CardRequestDto {

    @NotNull
    private Long number;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    private Long clientId;
}
