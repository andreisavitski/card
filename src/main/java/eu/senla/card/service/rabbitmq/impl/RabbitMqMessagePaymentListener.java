package eu.senla.card.service.rabbitmq.impl;

import eu.senla.card.dto.PaymentRequestMessageDto;
import eu.senla.card.dto.ResponseMessageDto;
import eu.senla.card.service.CardService;
import eu.senla.card.service.rabbitmq.RabbitMqMessageSender;
import eu.senla.card.util.MessageUtil;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static eu.senla.card.constant.AppConstants.RABBITMQ_QUEUE_REQUEST_FOR_PAYMENT;
import static eu.senla.card.constant.AppConstants.RABBITMQ_ROUTING_KEY_FOR_RESPONSE_PAYMENT;

@Service
@RequiredArgsConstructor
public class RabbitMqMessagePaymentListener {

    @Value(RABBITMQ_ROUTING_KEY_FOR_RESPONSE_PAYMENT)
    private String routingKeyForResponsePayment;

    private final CardService cardService;

    private final RabbitMqMessageSender sender;

    @RabbitListener(queues = {RABBITMQ_QUEUE_REQUEST_FOR_PAYMENT})
    public void acceptMoneyTransferRequest(@NotNull Message message) {
        final PaymentRequestMessageDto paymentRequestMessageDto =
                MessageUtil.convertFromMessage(message.getBody(), PaymentRequestMessageDto.class);
        final ResponseMessageDto responseMessageDto =
                cardService.makePayment(paymentRequestMessageDto);
        sender.convertAndSand(responseMessageDto, routingKeyForResponsePayment,
                message.getMessageProperties().getCorrelationId());
    }
}
