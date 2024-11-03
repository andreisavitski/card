package eu.senla.card.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;
import lombok.SneakyThrows;

@Setter
public class MessageUtil {

    @Setter
    private static ObjectMapper objectMapper;

    @SneakyThrows
    @NotNull
    public static <T> T convertFromMessage(byte[] json, @NotNull Class<T> clazz) {
        return objectMapper.readValue(json, clazz);
    }
}
