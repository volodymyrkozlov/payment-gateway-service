package com.volodymyrkozlow.paymentgateway.dto.request;

import com.volodymyrkozlow.paymentgateway.validation.InvoiceNotExists;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Data transfer object for submitting payment request.
 */
public record SubmitPaymentRequestDto(@NotNull @InvoiceNotExists Long invoice,
                                      @NotNull @Positive Long amount,
                                      @NotBlank String currency,
                                      @NotNull @Valid SubmitPaymentRequestCardHolderDto cardHolder,
                                      @NotNull @Valid SubmitPaymentRequestCardDto card) {
}
