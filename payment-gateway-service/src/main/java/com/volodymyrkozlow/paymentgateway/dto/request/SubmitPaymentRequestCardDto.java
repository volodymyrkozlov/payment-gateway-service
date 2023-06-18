package com.volodymyrkozlow.paymentgateway.dto.request;

import com.volodymyrkozlow.paymentgateway.validation.ValidExpiryDate;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Data transfer object for submitting payment request card.
 */
public record SubmitPaymentRequestCardDto(@NotBlank @Pattern(regexp = "^[0-9]{16}$",
    message = "payment card number must contains 16 digits") @CreditCardNumber String pan,
                                          @NotBlank @ValidExpiryDate String expiry,
                                          @NotBlank String cvv) {
}
