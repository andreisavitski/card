package eu.senla.card.mapper;

import eu.senla.card.dto.CardDto;
import eu.senla.card.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CardMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "number", target = "number")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "client.id", target = "clientId")
    CardDto toDto(Card card);
}
