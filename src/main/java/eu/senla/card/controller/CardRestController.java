package eu.senla.card.controller;

import eu.senla.card.dto.CardRequestDto;
import eu.senla.card.dto.CardResponseDto;
import eu.senla.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/card")
@RequiredArgsConstructor
public class CardRestController {

    private final CardService cardService;

    @PostMapping("/add")
    public CardResponseDto addCard(@RequestBody @Validated CardRequestDto cardRequestDto) {
        return cardService.addCard(cardRequestDto);
    }
}

