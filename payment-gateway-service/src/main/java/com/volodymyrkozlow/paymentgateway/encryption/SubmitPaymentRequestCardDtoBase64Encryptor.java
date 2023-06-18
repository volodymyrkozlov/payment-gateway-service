package com.volodymyrkozlow.paymentgateway.encryption;

import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestCardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Encryptor to encrypt {@link SubmitPaymentRequestCardDto}. Encryption output depends on {@link Base64Encoder}.
 */
@Component
@RequiredArgsConstructor
class SubmitPaymentRequestCardDtoBase64Encryptor implements Encryptor<SubmitPaymentRequestCardDto> {
    private final Base64Encoder base64Encoder;

    /**
     * Encrypts card pan and card expiry.
     *
     * @param request - must not be null.
     * @return {@link SubmitPaymentRequestCardDto}.
     */
    @Override
    public SubmitPaymentRequestCardDto encrypt(final SubmitPaymentRequestCardDto request) {
        final var encryptedCardPan = base64Encoder.encrypt(request.pan());
        final var encryptedCardExpiry = base64Encoder.encrypt(request.expiry());

        return new SubmitPaymentRequestCardDto(encryptedCardPan, encryptedCardExpiry, request.cvv());
    }
}
