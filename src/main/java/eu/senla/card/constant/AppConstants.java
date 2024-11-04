package eu.senla.card.constant;

public class AppConstants {

    public static final String RABBITMQ_QUEUE_REQUEST_FOR_GET_CARD =
            "${rabbitmq.queue_request_for_get_card.name}";

    public static final String RABBITMQ_QUEUE_RESPONSE_FOR_GET_CARD =
            "${rabbitmq.queue_response_for_get_card.name}";

    public static final String RABBITMQ_QUEUE_REQUEST_FOR_TRANSFER =
            "${rabbitmq.queue_request_for_transfer.name}";

    public static final String RABBITMQ_QUEUE_RESPONSE_FOR_TRANSFER =
            "${rabbitmq.queue_response_for_transfer.name}";

    public static final String RABBITMQ_QUEUE_REQUEST_FOR_PAYMENT =
            "${rabbitmq.queue_request_for_payment.name}";

    public static final String RABBITMQ_QUEUE_RESPONSE_FOR_PAYMENT =
            "${rabbitmq.queue_response_for_payment.name}";

    public static final String RABBITMQ_ROUTING_KEY_FOR_RESPONSE_GET_CARD =
            "${rabbitmq.routing_for_response_get_card.key}";

    public static final String RABBITMQ_ROUTING_KEY_FOR_RESPONSE_TRANSFER =
            "${rabbitmq.routing_for_response_transfer.key}";

    public static final String RABBITMQ_ROUTING_KEY_FOR_RESPONSE_PAYMENT =
            "${rabbitmq.routing_for_response_payment.key}";

    public static final String RABBITMQ_EXCHANGE_CARD =
            "${rabbitmq.exchange_card.name}";
}
