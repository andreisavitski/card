package eu.senla.card.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static eu.senla.card.constant.AppConstants.MAXIMUM_ATTEMPTS_FOR_SIMPLE_RETRY_POLICY;
import static eu.senla.card.constant.RabbitMqConstants.RABBITMQ_EXCHANGE_CARD;
import static eu.senla.card.constant.RabbitMqConstants.RABBITMQ_QUEUE_RESPONSE_FOR_ADD_CARD;
import static eu.senla.card.constant.RabbitMqConstants.RABBITMQ_QUEUE_RESPONSE_FOR_GET_CARD;
import static eu.senla.card.constant.RabbitMqConstants.RABBITMQ_QUEUE_RESPONSE_FOR_OPEN_DEPOSIT;
import static eu.senla.card.constant.RabbitMqConstants.RABBITMQ_QUEUE_RESPONSE_FOR_PAYMENT;
import static eu.senla.card.constant.RabbitMqConstants.RABBITMQ_QUEUE_RESPONSE_FOR_TRANSFER;
import static eu.senla.card.constant.RabbitMqConstants.RABBITMQ_QUEUE_RESPONSE_FOR_UPDATE_DEPOSIT;
import static eu.senla.card.constant.RabbitMqConstants.RABBITMQ_ROUTING_KEY_FOR_RESPONSE_ADD_CARD;
import static eu.senla.card.constant.RabbitMqConstants.RABBITMQ_ROUTING_KEY_FOR_RESPONSE_GET_CARD;
import static eu.senla.card.constant.RabbitMqConstants.RABBITMQ_ROUTING_KEY_FOR_RESPONSE_OPEN_DEPOSIT;
import static eu.senla.card.constant.RabbitMqConstants.RABBITMQ_ROUTING_KEY_FOR_RESPONSE_PAYMENT;
import static eu.senla.card.constant.RabbitMqConstants.RABBITMQ_ROUTING_KEY_FOR_RESPONSE_TRANSFER;

@Slf4j
@Configuration
public class RabbitMqConfiguration {

    @Value(RABBITMQ_QUEUE_RESPONSE_FOR_GET_CARD)
    private String queueResponseForGetCard;

    @Value(RABBITMQ_QUEUE_RESPONSE_FOR_ADD_CARD)
    private String queueResponseForAddCard;

    @Value(RABBITMQ_QUEUE_RESPONSE_FOR_TRANSFER)
    private String queueResponseForTransfer;

    @Value(RABBITMQ_QUEUE_RESPONSE_FOR_PAYMENT)
    private String queueResponseForPayment;

    @Value(RABBITMQ_QUEUE_RESPONSE_FOR_OPEN_DEPOSIT)
    private String queueResponseForOpenDeposit;

    @Value(RABBITMQ_QUEUE_RESPONSE_FOR_UPDATE_DEPOSIT)
    private String queueResponseForUpdateDeposit;

    @Value(RABBITMQ_EXCHANGE_CARD)
    private String exchangeCard;

    @Value(RABBITMQ_ROUTING_KEY_FOR_RESPONSE_GET_CARD)
    private String routingKeyForResponseGetCard;

    @Value(RABBITMQ_ROUTING_KEY_FOR_RESPONSE_ADD_CARD)
    private String routingKeyForResponseAddCard;

    @Value(RABBITMQ_ROUTING_KEY_FOR_RESPONSE_TRANSFER)
    private String routingKeyForResponseTransfer;

    @Value(RABBITMQ_ROUTING_KEY_FOR_RESPONSE_PAYMENT)
    private String routingKeyForResponsePayment;

    @Value(RABBITMQ_ROUTING_KEY_FOR_RESPONSE_OPEN_DEPOSIT)
    private String routingKeyForResponseOpenDeposit;

    @Bean
    public Queue queueResponseForGetCard() {
        return new Queue(queueResponseForGetCard);
    }

    @Bean
    public Queue queueResponseForAddCard() {
        return new Queue(queueResponseForAddCard);
    }

    @Bean
    public Queue queueResponseForTransfer() {
        return new Queue(queueResponseForTransfer);
    }

    @Bean
    public Queue queueResponseForPayment() {
        return new Queue(queueResponseForPayment);
    }

    @Bean
    public Queue queueResponseForOpenDeposit() {
        return new Queue(queueResponseForOpenDeposit);
    }

    @Bean
    public Queue queueResponseForUpdateDeposit() {
        return new Queue(queueResponseForUpdateDeposit);
    }

    @Bean
    public DirectExchange exchangeCard() {
        return new DirectExchange(exchangeCard);
    }

    @Bean
    public Binding bindingForResponseGetCard() {
        return BindingBuilder.bind(queueResponseForGetCard())
                .to(exchangeCard())
                .with(routingKeyForResponseGetCard);
    }

    @Bean
    public Binding bindingForResponseAddCard() {
        return BindingBuilder.bind(queueResponseForAddCard())
                .to(exchangeCard())
                .with(routingKeyForResponseAddCard);
    }

    @Bean
    public Binding bindingForResponseTransfer() {
        return BindingBuilder.bind(queueResponseForTransfer())
                .to(exchangeCard())
                .with(routingKeyForResponseTransfer);
    }

    @Bean
    public Binding bindingForResponsePayment() {
        return BindingBuilder.bind(queueResponseForPayment())
                .to(exchangeCard())
                .with(routingKeyForResponsePayment);
    }

    @Bean
    public Binding bindingForResponseOpenDeposit() {
        return BindingBuilder.bind(queueResponseForOpenDeposit())
                .to(exchangeCard())
                .with(routingKeyForResponseOpenDeposit);
    }

    @Bean
    public Binding bindingForResponseUpdateDeposit() {
        return BindingBuilder.bind(queueResponseForUpdateDeposit())
                .to(exchangeCard())
                .with(queueResponseForUpdateDeposit);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setAdviceChain(RetryInterceptorBuilder.stateless()
                .maxAttempts(MAXIMUM_ATTEMPTS_FOR_SIMPLE_RETRY_POLICY)
                .recoverer(new RejectAndDontRequeueRecoverer())
                .build());
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }
}
