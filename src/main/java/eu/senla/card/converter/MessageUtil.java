package eu.senla.card.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.SneakyThrows;

@Setter
public class MessageUtil {

    @Setter
    private static ObjectMapper objectMapper;

    @SneakyThrows
    public static <T> T convertFromMessage(byte[] json, Class<T> clazz) {
        return objectMapper.readValue(json, clazz);
    }
}
