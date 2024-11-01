package eu.senla.card.converter;

import eu.senla.card.dto.ResponseMessage;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

import static eu.senla.card.constant.AppStatusConstant.BAD_REQUEST;
import static eu.senla.card.constant.AppStatusConstant.OK;

public class ResponseMessageUtil {

    @NotNull
    public static ResponseMessage ok(@NotNull Object data) {
        return createMessage(OK, data);
    }

    @NotNull
    public static ResponseMessage ok() {
        return createMessage(OK, new ArrayList<>());
    }

    @NotNull
    public static ResponseMessage badRequest() {
        return createMessage(BAD_REQUEST, new ArrayList<>());
    }

    @NotNull
    private static ResponseMessage createMessage(@NotNull String status, @NotNull Object data) {
        return ResponseMessage.builder()
                .status(status)
                .data(data)
                .build();
    }
}
