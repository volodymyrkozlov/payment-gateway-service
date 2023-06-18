package com.volodymyrkozlow.paymentgateway.encryption;

import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestCardHolderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Encryptor to encrypt {@link SubmitPaymentRequestCardHolderDto}. Encryption output depends on {@link Base64Encoder}.
 */
@Component
@RequiredArgsConstructor
class SubmitPaymentRequestCardHolderDtoBase64Encryptor implements Encryptor<SubmitPaymentRequestCardHolderDto> {
    private final Base64Encoder base64Encoder;

    /**
     * Encrypts cardholder name.
     *
     * @param cardHolderDto - must not be null.
     * @return {@link SubmitPaymentRequestCardHolderDto}.
     */
    @Override
    public SubmitPaymentRequestCardHolderDto encrypt(final SubmitPaymentRequestCardHolderDto cardHolderDto) {
        final var encryptedCardHolderName = base64Encoder.encrypt(cardHolderDto.name());

        return new SubmitPaymentRequestCardHolderDto(encryptedCardHolderName, cardHolderDto.email());
    }

}