package com.volodymyrkozlow.paymentgateway.encryption;

import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestCardHolderDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class SubmitPaymentRequestCardHolderDtoBase64EncryptorTest {
    @Mock
    private Base64Encoder base64Encoder;
    @InjectMocks
    private SubmitPaymentRequestCardHolderDtoBase64Encryptor submitPaymentRequestCardHolderDtoBase64Encryptor;

    @Test
    void testEncrypt() {
        //given
        final var submitPaymentRequestCardHolder = new SubmitPaymentRequestCardHolderDto("First Last", "email@domain.com");
        final var expectedEncryptedSubmitPaymentRequestCardHolder = new SubmitPaymentRequestCardHolderDto("First Last encrypted", "email@domain.com");

        when(base64Encoder.encrypt(submitPaymentRequestCardHolder.name())).thenReturn(expectedEncryptedSubmitPaymentRequestCardHolder.name());

        //when
        final var encryptedSubmitPaymentRequestCardHolder = submitPaymentRequestCardHolderDtoBase64Encryptor.encrypt(submitPaymentRequestCardHolder);

        //then
        assertEquals(expectedEncryptedSubmitPaymentRequestCardHolder, encryptedSubmitPaymentRequestCardHolder);
    }
}
