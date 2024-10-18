package eu.senla.card.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static eu.senla.card.constant.AppConstants.*;

@Slf4j
@Configuration
public class RabbitMqConfiguration {

    @Value(RABBITMQ_QUEUE_1)
    private String queue1;

    @Value(RABBITMQ_QUEUE_2)
    private String queue2;

    @Value(RABBITMQ_EXCHANGE)
    private String exchange;

    @Value(RABBITMQ_ROUTING1_KEY)
    private String routingJsonKey1;

    @Value(RABBITMQ_ROUTING2_KEY)
    private String routingJsonKey2;

    @Bean
    public Queue queue1() {
        return new Queue(queue1);
    }

    @Bean
    public Queue queue2() {
        return new Queue(queue2);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding binding1() {
        return BindingBuilder
                .bind(queue1())
                .to(exchange())
                .with(routingJsonKey1);
    }

    @Bean
    public Binding binding2() {
        return BindingBuilder
                .bind(queue2())
                .to(exchange())
                .with(routingJsonKey2);
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
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setErrorHandler(e -> {
            log.error("Err or listener: ", e);
        });
        return factory;
    }
}
