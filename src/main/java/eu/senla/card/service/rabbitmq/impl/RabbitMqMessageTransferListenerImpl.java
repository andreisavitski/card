package eu.senla.card.service.rabbitmq.impl;

import eu.senla.card.converter.MessageUtil;
import eu.senla.card.dto.ResponseMessage;
import eu.senla.card.dto.TransferRequestMessage;
import eu.senla.card.service.CardService;
import eu.senla.card.service.rabbitmq.RabbitMqMessageSender;
import eu.senla.card.service.rabbitmq.RabbitMqMessageTransferListener;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static eu.senla.card.constant.AppConstants.RABBITMQ_QUEUE_REQUEST_FOR_TRANSFER;
import static eu.senla.card.constant.AppConstants.RABBITMQ_ROUTING_KEY_FOR_RESPONSE_TRANSFER;

@Service
@RequiredArgsConstructor
public class RabbitMqMessageTransferListenerImpl implements RabbitMqMessageTransferListener {

    @Value(RABBITMQ_ROUTING_KEY_FOR_RESPONSE_TRANSFER)
    private String routingKeyForResponseTransfer;

    private final CardService cardService;

    private final RabbitMqMessageSender sender;

    @Override
    @RabbitListener(queues = {RABBITMQ_QUEUE_REQUEST_FOR_TRANSFER})
    public void acceptMoneyTransferRequest(Message message) {
        TransferRequestMessage requestMessage = MessageUtil.convertFromMessage(message.getBody(),
                TransferRequestMessage.class);
        ResponseMessage responseMessage = cardService.makeTransfer(requestMessage);
        sender.convertAndSand(responseMessage, routingKeyForResponseTransfer,
                message.getMessageProperties().getCorrelationId());
    }
}
