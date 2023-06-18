package com.volodymyrkozlow.paymentgateway.sanitation;

import com.volodymyrkozlow.paymentgateway.dto.CardHolderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Sanitizer to sanitize {@link CardHolderDto}. Sanitation output depends on {@link StringHider} implementation.
 */
@Component
@RequiredArgsConstructor
class CardHolderDtoSanitizer implements Sanitizer<CardHolderDto> {
    private final StringHider stringHider;

    /**
     * Sanitizes {@link CardHolderDto} by masking cardholder name.
     *
     * @param cardHolderDto - must not be null.
     * @return {@link CardHolderDto}.
     */
    @Override
    public CardHolderDto sanitize(final CardHolderDto cardHolderDto) {
        return new CardHolderDto(stringHider.maskAllCharacters(cardHolderDto.name()), cardHolderDto.email());
    }
}
