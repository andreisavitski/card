package eu.senla.card.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.senla.card.util.MessageUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public final class AppConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        MessageUtil.setObjectMapper(objectMapper);
        return objectMapper;
    }
}
