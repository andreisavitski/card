package eu.senla.card.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
public class ResponseMessageDto {

    private final String status;

    private final Object data;

    private final List<Exception> exceptions;
}
