package com.volodymyrkozlow.paymentgateway.validator;

import com.volodymyrkozlow.paymentgateway.property.PaymentGatewayProperties;
import com.volodymyrkozlow.paymentgateway.validation.ValidExpiryDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidExpiryDateValidatorTest {
    @Mock
    private PaymentGatewayProperties paymentGatewayProperties;
    private ValidExpiryDate.ValidExpiryDateValidator validExpiryDateValidator;

    @BeforeEach
    void init() {
        final var paymentCardProperty = new PaymentGatewayProperties.PaymentCardProperty();
        paymentCardProperty.setExpiryFormat("MMyy");
        paymentCardProperty.setExpiryZoneId("UTC");

        when(paymentGatewayProperties.getCard()).thenReturn(paymentCardProperty);
        this.validExpiryDateValidator = new ValidExpiryDate.ValidExpiryDateValidator(paymentGatewayProperties);
    }

    @Test
    void testIsValid() {
        //given
        final var expiry = "0699";

        //when
        final var isValid = validExpiryDateValidator.isValid(expiry, null);

        //then
        assertTrue(isValid);
    }

    @Test
    void testIsValidWithExpiryInThePast() {
        //given
        final var expiry = "0621";

        //when
        final var isValid = validExpiryDateValidator.isValid(expiry, null);

        //then
        assertFalse(isValid);
    }

    @Test
    void testIsValidWithParseException() {
        //given
        final var expiry = "9921";

        //when
        final var isValid = validExpiryDateValidator.isValid(expiry, null);

        //then
        assertFalse(isValid);
    }
}
