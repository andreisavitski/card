package eu.senla.card.service.message;

import eu.senla.card.converter.MessageUtil;
import eu.senla.card.dto.ClientCardRequest;
import eu.senla.card.dto.ClientCardResponse;
import eu.senla.card.dto.TransferRequestMessage;
import eu.senla.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static eu.senla.card.constant.AppConstants.*;

@Service
@RequiredArgsConstructor
public class RabbitMqListener {

    @Value(RABBITMQ_EXCHANGE)
    private String exchange;

    @Value(RABBITMQ_ROUTING2_KEY)
    private String routing2JsonKey;

    @Value(RABBITMQ_ROUTING4_KEY)
    private String routing4JsonKey;

    private final CardService cardService;

    private final RabbitTemplate rabbitTemplate;

    private final MessageUtil messageUtil;


    @RabbitListener(queues = {RABBITMQ_QUEUE_1})
    public void acceptRequestToReceiveAllCards(Message message) {
        ClientCardRequest client = messageUtil.convertFromMessage(message.getBody(), ClientCardRequest.class);
        List<ClientCardResponse> cards = cardService.findCardByClientId(client.getId());
        convertAndSand(cards, routing2JsonKey, message.getMessageProperties().getCorrelationId());

        //вернуть DTO

    }

    @RabbitListener(queues = {RABBITMQ_QUEUE_3})
    public void acceptMoneyTransferRequest(Message message) {
        TransferRequestMessage requestMessage = messageUtil.convertFromMessage(message.getBody(),
                TransferRequestMessage.class);
        Boolean responseMessage = cardService.makeTransfer(requestMessage);
        convertAndSand(responseMessage, routing4JsonKey, message.getMessageProperties().getCorrelationId());
    }

    private void convertAndSand(Object o, String routingJsonKey, String correlationId) {
        rabbitTemplate.convertAndSend(
                exchange,
                routingJsonKey,
                o,
                messagePostProcessor -> {
                    messagePostProcessor.getMessageProperties().setCorrelationId(correlationId);
                    return messagePostProcessor;
                }
        );
    }
}
