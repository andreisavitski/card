package eu.senla.card.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.springframework.http.HttpStatusCode;

@Getter
@Builder
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {

    private HttpStatusCode status;

    private String message;
}
