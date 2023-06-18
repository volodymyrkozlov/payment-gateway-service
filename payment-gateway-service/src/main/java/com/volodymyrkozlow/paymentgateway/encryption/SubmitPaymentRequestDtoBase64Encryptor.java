package com.volodymyrkozlow.paymentgateway.encryption;

import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Encryptor to encrypt {@link SubmitPaymentRequestDto}. Encryption output depends on
 * {@link SubmitPaymentRequestCardHolderDtoBase64Encryptor} and {@link SubmitPaymentRequestCardDtoBase64Encryptor}
 * implementations.
 */
@Component
@RequiredArgsConstructor
public class SubmitPaymentRequestDtoBase64Encryptor implements Encryptor<SubmitPaymentRequestDto> {
    private final SubmitPaymentRequestCardHolderDtoBase64Encryptor submitPaymentRequestCardHolderDtoBase64Encryptor;
    private final SubmitPaymentRequestCardDtoBase64Encryptor submitPaymentRequestCardDtoBase64Encryptor;

    /**
     * Encrypts {@link SubmitPaymentRequestDto}.
     *
     * @param request - must not be null.
     * @return {@link SubmitPaymentRequestDto}.
     */
    @Override
    public SubmitPaymentRequestDto encrypt(final SubmitPaymentRequestDto request) {
        return new SubmitPaymentRequestDto(
            request.invoice(),
            request.amount(),
            request.currency(),
            submitPaymentRequestCardHolderDtoBase64Encryptor.encrypt(request.cardHolder()),
            submitPaymentRequestCardDtoBase64Encryptor.encrypt(request.card())
        );
    }
}
