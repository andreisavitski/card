package eu.senla.card.constant;

public class AppConstants {

    public static final String RABBITMQ_QUEUE_REQUEST_FOR_GET_CARD = "${rabbitmq.queue1.name}";

    public static final String RABBITMQ_QUEUE_RESPONSE_FOR_GET_CARD = "${rabbitmq.queue2.name}";

    public static final String RABBITMQ_QUEUE_REQUEST_FOR_TRANSFER = "${rabbitmq.queue3.name}";

    public static final String RABBITMQ_QUEUE_RESPONSE_FOR_TRANSFER = "${rabbitmq.queue4.name}";

    public static final String RABBITMQ_EXCHANGE = "${rabbitmq.exchange.name}";

    public static final String RABBITMQ_ROUTING_KEY_FOR_RESPONSE_GET_CARD = "${rabbitmq.routing2.key}";

    public static final String RABBITMQ_ROUTING_KEY_FOR_RESPONSE_TRANSFER = "${rabbitmq.routing4.key}";
}
