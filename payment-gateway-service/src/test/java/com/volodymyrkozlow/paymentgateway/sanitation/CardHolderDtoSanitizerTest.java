package com.volodymyrkozlow.paymentgateway.sanitation;

import com.volodymyrkozlow.paymentgateway.dto.CardHolderDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardHolderDtoSanitizerTest {
    @Mock
    private StringHider stringHider;
    @InjectMocks
    private CardHolderDtoSanitizer cardHolderDtoSanitizer;

    @Test
    void testSanitize() {
        //given
        final var cardHolderDto = new CardHolderDto("First Last", "email@domain.com");
        final var expectedCardHolderDto  = new CardHolderDto("First Last san", cardHolderDto.email());

        when(stringHider.maskAllCharacters(cardHolderDto.name())).thenReturn(expectedCardHolderDto.name());

        //when
        final var sanitizedCardHolderDto = cardHolderDtoSanitizer.sanitize(cardHolderDto);

        //then
        assertEquals(expectedCardHolderDto, sanitizedCardHolderDto);
    }
}
