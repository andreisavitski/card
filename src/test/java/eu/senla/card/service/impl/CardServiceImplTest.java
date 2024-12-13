//package eu.senla.card.service.impl;
//
//import eu.senla.card.dto.CardDto;
//import eu.senla.card.dto.ResponseMessageDto;
//import eu.senla.card.entity.Card;
//import eu.senla.card.entity.Client;
//import eu.senla.card.mapper.CardMapper;
//import eu.senla.card.repository.CardRepository;
//import eu.senla.card.service.CardService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//class CardServiceImplTest {
//
//    @Mock
//    private CardRepository cardRepository;
//
//    @Mock
//    private CardMapper cardMapper;
//
//    @InjectMocks
//    private CardService cardService; // Это класс, где находится ваш метод findCardByClientId
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testFindCardByClientId() {
//        // Подготовка данных
//        Client client = new Client();
//        client.setId(1L);
//
//        Card card1 = new Card(1L, 1234567891011121L, new BigDecimal(0), client);
//        Card card2 = new Card(2L, 9876543210987654L, new BigDecimal(1), client);
//
//        CardDto cardDto1 = new CardDto(1L, 1234567891011121L, new BigDecimal(0), client.getId());
//        CardDto cardDto2 = new CardDto(2L, 9876543210987654L, new BigDecimal(2), client.getId());
//
//        List<Card> mockCards = List.of(card1, card2);
//        List<CardDto> mockCardDtos = List.of(cardDto1, cardDto2);
//
//        // Настройка моков
//        when(cardRepository.findByClientId(client.getId())).thenReturn(mockCards);
//        when(cardMapper.toDto(card1)).thenReturn(cardDto1);
//        when(cardMapper.toDto(card2)).thenReturn(cardDto2);
//
//        // Выполнение метода
//        ResponseMessageDto result = cardService.findCardByClientId(client.getId());
//
//        // Проверка
//        assertNotNull(result);
//        assertEquals(mockCardDtos, result.getData());
//
//        // Убедимся, что зависимости вызывались корректно
//        verify(cardRepository, times(1)).findByClientId(client.getId());
//        verify(cardMapper, times(1)).toDto(card1);
//        verify(cardMapper, times(1)).toDto(card2);
//    }
//}