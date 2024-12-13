package eu.senla.card.service;

import eu.senla.card.dto.DepositOpenerDto;
import eu.senla.card.dto.DepositUpdaterDto;
import eu.senla.card.dto.ResponseMessageDto;
import jakarta.validation.constraints.NotNull;

public interface DepositService {

    @NotNull
    ResponseMessageDto openDeposit(@NotNull DepositOpenerDto depositOpenerDto);

    @NotNull
    ResponseMessageDto updateDeposit(@NotNull DepositUpdaterDto depositUpdaterDto);
}
