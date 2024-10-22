package eu.senla.card.controller;

import eu.senla.card.dto.CardRequestDto;
import eu.senla.card.dto.CardResponseDto;
import eu.senla.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/card")
@RequiredArgsConstructor
public class CardRestController {

    private final CardService cardService;

    @PostMapping("/add")
    public CardResponseDto addCard(@RequestBody @Validated CardRequestDto cardRequestDto) {
        return cardService.addCard(cardRequestDto);
    }

    @GetMapping("/get/{id}")
    public CardResponseDto getCardById(@PathVariable("id") Long clientId) {
        return cardService.getCardById(clientId);
    }
}

