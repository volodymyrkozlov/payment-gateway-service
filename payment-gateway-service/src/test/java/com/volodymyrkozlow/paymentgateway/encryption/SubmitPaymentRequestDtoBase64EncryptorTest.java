package com.volodymyrkozlow.paymentgateway.encryption;

import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestCardDto;
import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestCardHolderDto;
import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubmitPaymentRequestDtoBase64EncryptorTest {
    @Mock
    private SubmitPaymentRequestCardHolderDtoBase64Encryptor submitPaymentRequestCardHolderDtoBase64Encryptor;
    @Mock
    private SubmitPaymentRequestCardDtoBase64Encryptor submitPaymentRequestCardDtoBase64Encryptor;
    @InjectMocks
    private SubmitPaymentRequestDtoBase64Encryptor submitPaymentRequestDtoBase64Encryptor;

    @Test
    void testEncrypt() {
        //given
        final var submitPaymentRequestCard = new SubmitPaymentRequestCardDto("4200000000000001", "0624", "789");
        final var submitPaymentRequestCardHolder = new SubmitPaymentRequestCardHolderDto("First Last", "email@domain.com");
        final var submitPaymentRequest = new SubmitPaymentRequestDto(1234567L, 1299L, "EUR", submitPaymentRequestCardHolder, submitPaymentRequestCard);

        when(submitPaymentRequestCardHolderDtoBase64Encryptor.encrypt(submitPaymentRequestCardHolder)).thenReturn(submitPaymentRequestCardHolder);
        when(submitPaymentRequestCardDtoBase64Encryptor.encrypt(submitPaymentRequestCard)).thenReturn(submitPaymentRequestCard);

        //when
        final var encryptedSubmitPaymentRequestDto = submitPaymentRequestDtoBase64Encryptor.encrypt(submitPaymentRequest);

        //then
        assertEquals(submitPaymentRequest, encryptedSubmitPaymentRequestDto);
    }
}
