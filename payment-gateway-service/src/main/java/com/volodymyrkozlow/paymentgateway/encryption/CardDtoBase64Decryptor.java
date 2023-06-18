package com.volodymyrkozlow.paymentgateway.encryption;

import com.volodymyrkozlow.paymentgateway.dto.CardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Decryptor to decrypt {@link CardDto}. Decryption output depends on {@link Base64Decoder}.
 */
@Component
@RequiredArgsConstructor
class CardDtoBase64Decryptor implements Decryptor<CardDto> {
    private final Base64Decoder base64Decoder;

    /**
     * Decrypts card pan and expiry.
     *
     * @param cardDto - must not be null.
     * @return {@link CardDto}.
     */
    @Override
    public CardDto decrypt(final CardDto cardDto) {
        return new CardDto(base64Decoder.decode(cardDto.pan()), base64Decoder.decode(cardDto.expiry()));
    }
}
