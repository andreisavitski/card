package eu.senla.card.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Data
@Builder
@Jacksonized
public class DepositOpenerDto {

    private final UUID depositTypeId;

    private final UUID cardId;

    private final UUID clientId;
}
