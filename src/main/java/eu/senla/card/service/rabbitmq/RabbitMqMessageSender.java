package eu.senla.card.service.rabbitmq;

import jakarta.validation.constraints.NotNull;

public interface RabbitMqMessageSender {

    void convertAndSand(@NotNull Object o,
                        @NotNull String routingJsonKey,
                        @NotNull String correlationId);
}
