package eu.senla.card.dto;

import eu.senla.card.exception.ApplicationException;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
public class TransferResponseMessage {

    private List<ClientCardResponse> cards;

    private ApplicationException applicationException;
}
