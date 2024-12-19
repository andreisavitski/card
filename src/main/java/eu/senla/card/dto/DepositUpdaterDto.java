package eu.senla.card.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@Jacksonized
public class DepositUpdaterDto {

    private final UUID depositId;

    private final BigDecimal contributionAmount;
}
