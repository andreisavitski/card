package eu.senla.card.service.rabbitmq;

import org.springframework.amqp.core.Message;

public interface RabbitMqMessageCardListener {

    void acceptRequestToReceiveAllCards(Message message);
}
