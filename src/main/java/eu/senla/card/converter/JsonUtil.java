package eu.senla.card.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class JsonUtil {

    ObjectMapper objectMapper = new ObjectMapper();

    public String toJson(List<Object> list) throws JsonProcessingException {
        return objectMapper.writeValueAsString(list);
    }

    public <T> T fromJson(String json, Class<T> clazz) throws IOException {
        return objectMapper.readValue(json, clazz);
    }
}
