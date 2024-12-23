package eu.senla.card.service.rabbitmq.impl;

import eu.senla.card.dto.ResponseMessageDto;
import eu.senla.card.dto.TransferRequestMessageDto;
import eu.senla.card.service.CardService;
import eu.senla.card.service.rabbitmq.RabbitMqMessageSender;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static eu.senla.card.constant.RabbitMqConstants.RABBITMQ_QUEUE_REQUEST_FOR_TRANSFER;
import static eu.senla.card.constant.RabbitMqConstants.RABBITMQ_ROUTING_KEY_FOR_RESPONSE_TRANSFER;
import static eu.senla.card.util.MessageUtil.convertFromMessage;

@Service
@RequiredArgsConstructor
public class RabbitMqTransferListener {

    @Value(RABBITMQ_ROUTING_KEY_FOR_RESPONSE_TRANSFER)
    private String routingKeyForResponseTransfer;

    private final CardService cardService;

    private final RabbitMqMessageSender sender;

    @RabbitListener(queues = {RABBITMQ_QUEUE_REQUEST_FOR_TRANSFER})
    public void acceptMoneyTransferRequest(@NotNull Message message) {
        final TransferRequestMessageDto requestMessage =
                convertFromMessage(message.getBody(), TransferRequestMessageDto.class);
        final ResponseMessageDto responseMessageDto = cardService.makeTransfer(requestMessage);
        sender.convertAndSand(responseMessageDto, routingKeyForResponseTransfer,
                message.getMessageProperties().getCorrelationId());
    }
}
