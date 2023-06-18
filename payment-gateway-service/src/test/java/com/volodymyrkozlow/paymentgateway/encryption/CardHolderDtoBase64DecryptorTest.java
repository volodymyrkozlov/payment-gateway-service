package com.volodymyrkozlow.paymentgateway.encryption;

import com.volodymyrkozlow.paymentgateway.dto.CardHolderDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardHolderDtoBase64DecryptorTest {
    @Mock
    private Base64Decoder base64Decoder;
    @InjectMocks
    private CardHolderDtoBase64Decryptor cardHolderDtoBase64Decryptor;

    @Test
    void testDecode() {
        //given
        final var cardHolder = new CardHolderDto("First Last", "email@domain.com");
        final var decodedCardHolder = new CardHolderDto( "First Last decoded", cardHolder.email());

        when(base64Decoder.decode(cardHolder.name())).thenReturn(decodedCardHolder.name());

        //when
        final var decryptedCardHolder = cardHolderDtoBase64Decryptor.decrypt(cardHolder);

        //then
        assertEquals(decodedCardHolder, decryptedCardHolder);
    }
}
