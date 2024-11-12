package eu.senla.card.converter;

import eu.senla.card.dto.ResponseMessageDtoTest;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

import static eu.senla.card.constant.AppStatusConstant.BAD_REQUEST;
import static eu.senla.card.constant.AppStatusConstant.OK;

public class ResponseMessageUtil {

    @NotNull
    public static ResponseMessageDtoTest ok(@NotNull Object data) {
        return createMessage(OK, data);
    }

    @NotNull
    public static ResponseMessageDtoTest ok() {
        return createMessage(OK, new ArrayList<>());
    }

    @NotNull
    public static ResponseMessageDtoTest badRequest() {
        return createMessage(BAD_REQUEST, new ArrayList<>());
    }

    @NotNull
    private static ResponseMessageDtoTest createMessage(@NotNull String status, @NotNull Object data) {
        return ResponseMessageDtoTest.builder()
                .status(status)
                .data(data)
                .exceptions(new ArrayList<>())
                .build();
    }
}
