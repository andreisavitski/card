package eu.senla.card.service.rabbitmq.impl;

import eu.senla.card.dto.DepositOpenerDto;
import eu.senla.card.dto.DepositUpdaterDto;
import eu.senla.card.dto.ResponseMessageDto;
import eu.senla.card.service.DepositService;
import eu.senla.card.service.rabbitmq.RabbitMqMessageSender;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static eu.senla.card.constant.RabbitMqConstants.RABBITMQ_QUEUE_REQUEST_FOR_OPEN_DEPOSIT;
import static eu.senla.card.constant.RabbitMqConstants.RABBITMQ_QUEUE_REQUEST_FOR_UPDATE_DEPOSIT;
import static eu.senla.card.constant.RabbitMqConstants.RABBITMQ_ROUTING_KEY_FOR_RESPONSE_OPEN_DEPOSIT;
import static eu.senla.card.constant.RabbitMqConstants.RABBITMQ_ROUTING_KEY_FOR_RESPONSE_UPDATE_DEPOSIT;
import static eu.senla.card.util.MessageUtil.convertFromMessage;

@Service
@RequiredArgsConstructor
public class RabbitMqDepositListener {

    @Value(RABBITMQ_ROUTING_KEY_FOR_RESPONSE_OPEN_DEPOSIT)
    private String routingKeyForResponseOpenDeposit;

    @Value(RABBITMQ_ROUTING_KEY_FOR_RESPONSE_UPDATE_DEPOSIT)
    private String routingKeyForResponseUpdateDeposit;

    private final DepositService depositService;

    private final RabbitMqMessageSender sender;

    @RabbitListener(queues = {RABBITMQ_QUEUE_REQUEST_FOR_OPEN_DEPOSIT})
    public void acceptRequestToOpenDeposit(@NotNull Message message) {
        final DepositOpenerDto deposit =
                convertFromMessage(message.getBody(), DepositOpenerDto.class);
        final ResponseMessageDto responseMessageDto = depositService.openDeposit(deposit);
        sender.convertAndSand(responseMessageDto, routingKeyForResponseOpenDeposit,
                message.getMessageProperties().getCorrelationId());
    }

    @RabbitListener(queues = {RABBITMQ_QUEUE_REQUEST_FOR_UPDATE_DEPOSIT})
    public void acceptRequestToUpdateDeposit(@NotNull Message message) {
        final DepositUpdaterDto deposit =
                convertFromMessage(message.getBody(), DepositUpdaterDto.class);
        final ResponseMessageDto responseMessageDto = depositService.updateDeposit(deposit);
        sender.convertAndSand(responseMessageDto, routingKeyForResponseUpdateDeposit,
                message.getMessageProperties().getCorrelationId());
    }
}
