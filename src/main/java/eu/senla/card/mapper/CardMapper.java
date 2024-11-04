package eu.senla.card.mapper;

import eu.senla.card.dto.CardDto;
import eu.senla.card.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardMapper {

    @Mapping(source = "client.id", target = "clientId")
    CardDto toClientCardResponse(Card card);
}
