package com.volodymyrkozlow.paymentgateway.sanitation;

import com.volodymyrkozlow.paymentgateway.dto.CardDto;
import com.volodymyrkozlow.paymentgateway.dto.CardHolderDto;
import com.volodymyrkozlow.paymentgateway.dto.TransactionDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionDtoSanitizerTest {
    @Mock
    private CardDtoSanitizer cardDtoSanitizer;
    @Mock
    private CardHolderDtoSanitizer cardHolderDtoSanitizer;
    @InjectMocks
    private TransactionDtoSanitizer transactionDtoSanitizer;

    @Test
    void testSanitize() {
        //given
        final var cardHolderDto = new CardHolderDto("First Last", "email@domain.com");
        final var cardDto = new CardDto("4200000000000001", "0624");
        final var transactionDto = new TransactionDto(1234567L, 1299L, "EUR", cardHolderDto, cardDto);

        when(cardDtoSanitizer.sanitize(cardDto)).thenReturn(cardDto);
        when(cardHolderDtoSanitizer.sanitize(cardHolderDto)).thenReturn(cardHolderDto);

        //when
        final var sanitizedTransactionDto = transactionDtoSanitizer.sanitize(transactionDto);

        //then
        assertEquals(transactionDto, sanitizedTransactionDto);
    }
}
