package eu.senla.card.converter;

import eu.senla.card.dto.ResponseMessageDto;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

import static eu.senla.card.constant.AppStatusConstant.BAD_REQUEST;
import static eu.senla.card.constant.AppStatusConstant.OK;

public class ResponseMessageUtil {

    @NotNull
    public static ResponseMessageDto ok(@NotNull Object data) {
        return createMessage(OK, data);
    }

    @NotNull
    public static ResponseMessageDto ok() {
        return createMessage(OK, new ArrayList<>());
    }

    @NotNull
    public static ResponseMessageDto badRequest() {
        return createMessage(BAD_REQUEST, new ArrayList<>());
    }

    @NotNull
    private static ResponseMessageDto createMessage(@NotNull String status, @NotNull Object data) {
        return ResponseMessageDto.builder()
                .status(status)
                .data(data)
                .exceptions(new ArrayList<>())
                .build();
    }
}
