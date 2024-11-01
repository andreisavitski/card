package eu.senla.card.service.rabbitmq;

public interface RabbitMqMessageSender {

    void convertAndSand(Object o, String routingJsonKey, String correlationId);
}
