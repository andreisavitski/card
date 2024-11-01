package eu.senla.card.service.rabbitmq.impl;

import eu.senla.card.converter.MessageUtil;
import eu.senla.card.dto.ClientCardRequest;
import eu.senla.card.dto.ResponseMessage;
import eu.senla.card.service.CardService;
import eu.senla.card.service.rabbitmq.RabbitMqMessageCardListener;
import eu.senla.card.service.rabbitmq.RabbitMqMessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static eu.senla.card.constant.AppConstants.RABBITMQ_QUEUE_REQUEST_FOR_GET_CARD;
import static eu.senla.card.constant.AppConstants.RABBITMQ_ROUTING_KEY_FOR_RESPONSE_GET_CARD;

@Service
@RequiredArgsConstructor
public class RabbitMqMessageCardListenerImpl implements RabbitMqMessageCardListener {

    @Value(RABBITMQ_ROUTING_KEY_FOR_RESPONSE_GET_CARD)
    private String routingKeyForResponseGetCard;

    private final CardService cardService;

    private final RabbitMqMessageSender sender;

    @Override
    @RabbitListener(queues = {RABBITMQ_QUEUE_REQUEST_FOR_GET_CARD})
    public void acceptRequestToReceiveAllCards(Message message) {
        ClientCardRequest client = MessageUtil.convertFromMessage(message.getBody(), ClientCardRequest.class);
        ResponseMessage responseMessage = cardService.findCardByClientId(client.getId());
        sender.convertAndSand(responseMessage, routingKeyForResponseGetCard,
                message.getMessageProperties().getCorrelationId());
    }
}
