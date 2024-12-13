package eu.senla.card.util;

import eu.senla.card.dto.ResponseMessageDto;
import jakarta.validation.constraints.NotNull;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

import static eu.senla.card.constant.AppConstants.BAD_REQUEST;
import static eu.senla.card.constant.AppConstants.OK;

@UtilityClass
public class ResponseMessageUtil {

    @NotNull
    public static ResponseMessageDto ok(@NotNull Object data) {
        return createMessage(OK, data, new ArrayList<>());
    }

    @NotNull
    public static ResponseMessageDto ok() {
        return createMessage(OK, new ArrayList<>(), new ArrayList<>());
    }

    @NotNull
    public static ResponseMessageDto badRequest() {
        return createMessage(BAD_REQUEST, new ArrayList<>(), new ArrayList<>());
    }

    @NotNull
    public static ResponseMessageDto badRequest(@NotNull List<Exception> exceptions) {
        return createMessage(BAD_REQUEST, new ArrayList<>(), exceptions);
    }

    @NotNull
    public static ResponseMessageDto badRequest(@NotNull Object data) {
        return createMessage(BAD_REQUEST, data, new ArrayList<>());
    }

    @NotNull
    private static ResponseMessageDto createMessage(@NotNull String status,
                                                    @NotNull Object data,
                                                    @NotNull List<Exception> exceptions) {
        return ResponseMessageDto.builder()
                .status(status)
                .data(data)
                .exceptions(exceptions)
                .build();
    }
}
