package com.volodymyrkozlow.paymentgateway.sanitation;

import com.volodymyrkozlow.paymentgateway.dto.CardDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardDtoSanitizerTest {
    @Mock
    private StringHider stringHider;
    @InjectMocks
    private CardDtoSanitizer cardDtoSanitizer;

    @Test
    void testSanitize() {
        //given
        final var cardDto = new CardDto("4200000000000001", "0624");
        final var expectedSanitizedCardDto = new CardDto("4200000000000001 san", "0624 san");

        when(stringHider.maskCardNumber(cardDto.pan())).thenReturn(expectedSanitizedCardDto.pan());
        when(stringHider.maskAllCharacters(cardDto.expiry())).thenReturn(expectedSanitizedCardDto.expiry());

        //when
        final var sanitizedCardDto = cardDtoSanitizer.sanitize(cardDto);

        //then
        assertEquals(expectedSanitizedCardDto, sanitizedCardDto);
    }
}
