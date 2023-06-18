package com.volodymyrkozlow.paymentgateway.encryption;

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
public class TransactionDtoBase64DecryptorTest {
    @Mock
    private CardHolderDtoBase64Decryptor cardHolderDtoBase64Decryptor;
    @Mock
    private CardDtoBase64Decryptor cardDtoBase64Decryptor;
    @InjectMocks
    private TransactionDtoBase64Decryptor transactionDtoBase64Decryptor;

    @Test
    void testDecrypt() {
        //given
        final var cardHolderDto = new CardHolderDto("First Last", "email@domain.com");
        final var cardDto = new CardDto("4200000000000001", "0624");
        final var transactionDto = new TransactionDto(1234567L, 1299L, "EUR", cardHolderDto, cardDto);

        when(cardDtoBase64Decryptor.decrypt(cardDto)).thenReturn(cardDto);
        when(cardHolderDtoBase64Decryptor.decrypt(cardHolderDto)).thenReturn(cardHolderDto);

        //when
        final var decryptedTransactionDto = transactionDtoBase64Decryptor.decrypt(transactionDto);

        //then
        assertEquals(transactionDto, decryptedTransactionDto);
    }
}
