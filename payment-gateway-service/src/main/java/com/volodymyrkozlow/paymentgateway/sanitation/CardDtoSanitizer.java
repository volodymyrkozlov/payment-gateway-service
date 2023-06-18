package com.volodymyrkozlow.paymentgateway.sanitation;

import com.volodymyrkozlow.paymentgateway.dto.CardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Sanitizer to sanitize {@link CardDto}. Sanitation output depends on {@link StringHider} implementation.
 */
@Component
@RequiredArgsConstructor
class CardDtoSanitizer implements Sanitizer<CardDto> {
    private final StringHider stringHider;

    /**
     * Sanitizes {@link CardDto} by masking card pan and card expiry.
     *
     * @param cardDto - must not be null.
     * @return {@link CardDto}.
     */
    @Override
    public CardDto sanitize(final CardDto cardDto) {
        return new CardDto(stringHider.maskCardNumber(cardDto.pan()), stringHider.maskAllCharacters(cardDto.expiry()));
    }
}
