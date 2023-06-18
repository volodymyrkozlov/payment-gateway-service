package com.volodymyrkozlow.paymentgateway.dto.error;

/**
 * Data transfer object for response error details.
 */
public record TransactionDeclinedErrorDetailsDto(String title,
                                                 String location) {
}
