package com.volodymyrkozlow.paymentgateway.sanitation;

import com.volodymyrkozlow.paymentgateway.property.PaymentGatewayProperties;
import org.springframework.stereotype.Component;

/**
 * A component to provide a possibility to sanitize strings. Sanitizing character depends on
 * defined {@link PaymentGatewayProperties}.
 */
@Component
class StringHider {
    private final String sanitizerCharacter;

    public StringHider(final PaymentGatewayProperties paymentGatewayProperties) {
        this.sanitizerCharacter = paymentGatewayProperties.getSanitizerCharacter();
    }

    /**
     * Sanitizes a card number by hiding first 12 digits.
     *
     * @param value - must not be null.
     * @return sanitized card number.
     */
    public String maskCardNumber(final String value) {
        return value.replaceAll(".(?=(?:\\D*\\d){4})", sanitizerCharacter);
    }

    /**
     * Sanitizes a string value by hiding all characters.
     *
     * @param value - must not be null.
     * @return sanitized string.
     */
    public String maskAllCharacters(final String value) {
        return sanitizerCharacter.repeat(value.length());
    }
}
