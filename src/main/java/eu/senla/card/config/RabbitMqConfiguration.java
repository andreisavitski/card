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

import static eu.senla.card.constant.AppConstants.*;

@Slf4j
@Configuration
public class RabbitMqConfiguration {

    @Value(RABBITMQ_QUEUE_RESPONSE_FOR_GET_CARD)
    private String queueResponseForGetCard;

    @Value(RABBITMQ_QUEUE_RESPONSE_FOR_TRANSFER)
    private String queueResponseForTransfer;

    @Value(RABBITMQ_EXCHANGE_CARD_TRANSFER)
    private String exchangeCardTransfer;

    @Value(RABBITMQ_ROUTING_KEY_FOR_RESPONSE_GET_CARD)
    private String routingKeyForResponseGetCard;

    @Value(RABBITMQ_ROUTING_KEY_FOR_RESPONSE_TRANSFER)
    private String routingKeyForResponseTransfer;

    @Bean
    public Queue queueResponseForGetCard() {
        return new Queue(queueResponseForGetCard);
    }

    @Bean
    public Queue queueResponseForTransfer() {
        return new Queue(queueResponseForTransfer);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchangeCardTransfer);
    }

    @Bean
    public Binding bindingForResponseGetCard() {
        return BindingBuilder
                .bind(queueResponseForGetCard())
                .to(exchange())
                .with(routingKeyForResponseGetCard);
    }

    @Bean
    public Binding bindingForResponseTransfer() {
        return BindingBuilder
                .bind(queueResponseForTransfer())
                .to(exchange())
                .with(routingKeyForResponseTransfer);
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
        factory.setAdviceChain(
                RetryInterceptorBuilder
                        .stateless()
                        .maxAttempts(5)
                        .recoverer(new RejectAndDontRequeueRecoverer())
                        .build()
        );
        factory.setConnectionFactory(connectionFactory);
        factory.setErrorHandler(e -> {
            log.error("Err or listener: ", e);
        });
        return factory;
    }
}
