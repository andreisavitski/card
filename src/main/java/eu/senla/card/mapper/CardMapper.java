package eu.senla.card.mapper;

import eu.senla.card.dto.CardRequestDto;
import eu.senla.card.dto.CardResponseDto;
import eu.senla.card.dto.ClientCardResponse;
import eu.senla.card.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardMapper {

    @Mapping(source = "client.id", target = "clientId")
    CardResponseDto toDto(Card card);

    @Mapping(source = "clientId", target = "client.id")
    Card toCard(CardRequestDto cardRequestDto);

    @Mapping(source = "client.id", target = "clientId")
    ClientCardResponse toClientCardResponse(Card card);
}
