package eu.senla.card.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageUtil {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    public <T> T convertFromMessage(byte[] json, Class<T> clazz) {
        return objectMapper.readValue(json, clazz);
    }
}
