package eu.senla.card.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.senla.card.util.MessageUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        MessageUtil.setObjectMapper(objectMapper);
        return objectMapper;
    }
}
