package com.volodymyrkozlow.paymentgateway.encryption;

import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestCardDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubmitPaymentRequestCardDtoBase64EncryptorTest {
    @Mock
    private Base64Encoder base64Encoder;
    @InjectMocks
    private SubmitPaymentRequestCardDtoBase64Encryptor submitPaymentRequestCardDtoBase64Encryptor;

    @Test
    void testEncrypt() {
        //given
        final var submitPaymentRequestCard = new SubmitPaymentRequestCardDto("4200000000000001", "0624", "789");
        final var expectedEncryptedSubmitPaymentRequestCard = new SubmitPaymentRequestCardDto("4200000000000001 encrypted", "0624 encrypted", submitPaymentRequestCard.cvv());

        when(base64Encoder.encrypt(submitPaymentRequestCard.pan())).thenReturn(expectedEncryptedSubmitPaymentRequestCard.pan());
        when(base64Encoder.encrypt(submitPaymentRequestCard.expiry())).thenReturn(expectedEncryptedSubmitPaymentRequestCard.expiry());

        //when
        final var encryptedSubmitPaymentRequestCard = submitPaymentRequestCardDtoBase64Encryptor.encrypt(submitPaymentRequestCard);

        //then
        assertEquals(expectedEncryptedSubmitPaymentRequestCard, encryptedSubmitPaymentRequestCard);
    }
}
