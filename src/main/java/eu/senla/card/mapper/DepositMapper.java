package eu.senla.card.mapper;

import eu.senla.card.dto.DepositDto;
import eu.senla.card.entity.Deposit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface DepositMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "depositType.id", target = "depositTypeId")
    @Mapping(source = "depositOpeningDate", target = "depositOpeningDate")
    @Mapping(source = "depositClosingDate", target = "depositClosingDate")
    @Mapping(source = "card.id", target = "cardId")
    @Mapping(source = "client.id", target = "clientId")
    DepositDto toDto(Deposit deposit);
}
