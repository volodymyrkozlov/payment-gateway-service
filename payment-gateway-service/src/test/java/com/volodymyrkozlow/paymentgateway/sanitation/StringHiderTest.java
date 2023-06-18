package com.volodymyrkozlow.paymentgateway.sanitation;

import com.volodymyrkozlow.paymentgateway.property.PaymentGatewayProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StringHiderTest {
    @Mock
    private PaymentGatewayProperties paymentGatewayProperties;
    private StringHider stringHider;

    @BeforeEach
    void init() {
        when(paymentGatewayProperties.getSanitizerCharacter()).thenReturn("*");
        this.stringHider = new StringHider(paymentGatewayProperties);
    }

    @Test
    void testMaskCardNumber() {
        //given
        final var cardNumber = "4200000000000001";

        //when
        final var maskedCardNumber = stringHider.maskCardNumber(cardNumber);

        //then
        assertEquals("************0001", maskedCardNumber);
    }

    @Test
    void maskAllCharacters() {
        //given
        final var cardNumber = "4200000000000001";

        //when
        final var maskedCardNumber = stringHider.maskAllCharacters(cardNumber);

        //then
        assertEquals("****************", maskedCardNumber);
    }
}
