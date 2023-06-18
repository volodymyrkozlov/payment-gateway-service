package com.volodymyrkozlow.paymentgateway.encryption;

import com.volodymyrkozlow.paymentgateway.dto.CardHolderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Decryptor to decrypt {@link CardHolderDto}. Decryption output depends on {@link Base64Decoder}.
 */
@Component
@RequiredArgsConstructor
class CardHolderDtoBase64Decryptor implements Decryptor<CardHolderDto> {
    private final Base64Decoder base64Decoder;

    /**
     * Decrypts cardholder name.
     *
     * @param cardHolderDto - must not be null.
     * @return {@link CardHolderDto}.
     */
    @Override
    public CardHolderDto decrypt(final CardHolderDto cardHolderDto) {
        return new CardHolderDto(base64Decoder.decode(cardHolderDto.name()), cardHolderDto.email());
    }
}
