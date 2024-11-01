package eu.senla.card.service.rabbitmq;

import org.springframework.amqp.core.Message;

public interface RabbitMqMessageTransferListener {

    void acceptMoneyTransferRequest(Message message);
}
