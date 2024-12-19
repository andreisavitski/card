package eu.senla.card.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Jacksonized
public class DepositDto {

    private final UUID id;

    private final UUID depositTypeId;

    private final LocalDateTime depositOpeningDate;

    private final LocalDateTime depositClosingDate;

    private final UUID cardId;

    private final UUID clientId;
}
