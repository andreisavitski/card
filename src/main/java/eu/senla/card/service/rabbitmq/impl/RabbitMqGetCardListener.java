package eu.senla.card.service.rabbitmq.impl;

import eu.senla.card.dto.ClientCardRequestDto;
import eu.senla.card.dto.ResponseMessageDto;
import eu.senla.card.service.CardService;
import eu.senla.card.service.rabbitmq.RabbitMqMessageSender;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static eu.senla.card.constant.RabbitMqConstants.RABBITMQ_QUEUE_REQUEST_FOR_GET_CARD;
import static eu.senla.card.constant.RabbitMqConstants.RABBITMQ_ROUTING_KEY_FOR_RESPONSE_GET_CARD;
import static eu.senla.card.util.MessageUtil.convertFromMessage;

@Service
@RequiredArgsConstructor
public class RabbitMqGetCardListener {

    @Value(RABBITMQ_ROUTING_KEY_FOR_RESPONSE_GET_CARD)
    private String routingKeyForResponseGetCard;

    private final CardService cardService;

    private final RabbitMqMessageSender sender;

    @RabbitListener(queues = {RABBITMQ_QUEUE_REQUEST_FOR_GET_CARD})
    public void acceptRequestToReceiveAllCards(@NotNull Message message) {
        final ClientCardRequestDto client =
                convertFromMessage(message.getBody(), ClientCardRequestDto.class);
        final ResponseMessageDto responseMessageDto =
                cardService.findCardByClientId(client.getId());
        sender.convertAndSand(responseMessageDto, routingKeyForResponseGetCard,
                message.getMessageProperties().getCorrelationId());
    }
}
