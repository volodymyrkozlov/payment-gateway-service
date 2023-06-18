package com.volodymyrkozlow.paymentgateway.encryption;

import com.volodymyrkozlow.paymentgateway.dto.CardDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardDtoBase64DecryptorTest {
    @Mock
    private Base64Decoder base64Decoder;
    @InjectMocks
    private CardDtoBase64Decryptor base64Decryptor;

    @Test
    void testDecode() {
        //given
        final var card = new CardDto("4200000000000001", "0624");
        final var decodedCard = new CardDto( "4200000000000001 decoded", "0624 decoded");

        when(base64Decoder.decode(card.pan())).thenReturn(decodedCard.pan());
        when(base64Decoder.decode(card.expiry())).thenReturn(decodedCard.expiry());

        //when
        final var decryptedCard = base64Decryptor.decrypt(card);

        //then
        assertEquals(decodedCard, decryptedCard);
    }
}
