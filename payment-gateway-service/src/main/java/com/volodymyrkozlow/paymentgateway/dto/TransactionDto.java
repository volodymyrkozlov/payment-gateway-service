package com.volodymyrkozlow.paymentgateway.dto;

/**
 * Data transfer object for transaction.
 */
public record TransactionDto(Long invoice,
                             Long amount,
                             String currency,
                             CardHolderDto cardHolder,
                             CardDto card) {
}
