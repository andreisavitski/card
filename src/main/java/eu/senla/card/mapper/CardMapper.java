package eu.senla.card.mapper;

import eu.senla.card.dto.ClientCardResponseDto;
import eu.senla.card.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardMapper {

    @Mapping(source = "client.id", target = "clientId")
    ClientCardResponseDto toClientCardResponse(Card card);
}
