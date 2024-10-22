package eu.senla.card.service.message;

import eu.senla.card.converter.JsonUtil;
import eu.senla.card.dto.ClientCardRequest;
import eu.senla.card.dto.ClientCardResponse;
import eu.senla.card.dto.TransferRequestMessage;
import eu.senla.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

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

    private final JsonUtil jsonUtil;

    @RabbitListener(queues = {RABBITMQ_QUEUE_1})
    public void acceptRequestToReceiveAllCards(String message, @Header("correlationId1") String correlationId)
            throws IOException {
        ClientCardRequest client = jsonUtil.fromJson(message, ClientCardRequest.class);
        List<ClientCardResponse> cards = cardService.findCardByClientId(client.getId());
        rabbitTemplate.convertAndSend(
                exchange,
                routing2JsonKey,
                cards,
                messagePostProcessor -> {
                    messagePostProcessor.getMessageProperties().setHeader("correlationId1", correlationId);
                    return messagePostProcessor;
                }
        );
    }

    @RabbitListener(queues = {RABBITMQ_QUEUE_3})
    public void acceptMoneyTransferRequest(String message, @Header("correlationId2") String correlationId)
            throws IOException, InterruptedException, TimeoutException {
        TransferRequestMessage transferRequestMessage = jsonUtil.fromJson(message, TransferRequestMessage.class);
        Boolean responseMessage = cardService.makeTransfer(transferRequestMessage);
        rabbitTemplate.convertAndSend(
                exchange,
                routing4JsonKey,
                responseMessage,
                messagePostProcessor -> {
                    messagePostProcessor.getMessageProperties().setHeader("correlationId2", correlationId);
                    return messagePostProcessor;
                }
        );
    }
}
