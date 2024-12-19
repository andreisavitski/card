package eu.senla.card.service.rabbitmq.impl;

import eu.senla.card.service.rabbitmq.RabbitMqMessageSender;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static eu.senla.card.constant.RabbitMqConstants.RABBITMQ_EXCHANGE_CARD;

@Service
@RequiredArgsConstructor
public class RabbitMqSenderImpl implements RabbitMqMessageSender {

    @Value(RABBITMQ_EXCHANGE_CARD)
    private String exchange;

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void convertAndSand(@NotNull Object message,
                               @NotNull String routingKey,
                               @NotNull String correlationId) {
        rabbitTemplate.convertAndSend(
                exchange,
                routingKey,
                message,
                messagePostProcessor -> {
                    messagePostProcessor.getMessageProperties().setCorrelationId(correlationId);
                    return messagePostProcessor;
                }
        );
    }
}
