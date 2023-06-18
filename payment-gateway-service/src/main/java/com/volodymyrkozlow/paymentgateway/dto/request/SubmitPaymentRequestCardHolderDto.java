package com.volodymyrkozlow.paymentgateway.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Data transfer object for submitting payment request cardholder.
 */
public record SubmitPaymentRequestCardHolderDto(@NotBlank String name,
                                                @NotBlank @Email String email) {
}
