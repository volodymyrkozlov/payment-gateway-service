package com.volodymyrkozlow.paymentgateway.sanitation;

import com.volodymyrkozlow.paymentgateway.dto.TransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Sanitizer to sanitize {@link TransactionDto}. Sanitation output depends on {@link CardDtoSanitizer} and
 * {@link CardHolderDtoSanitizer} implementations.
 */
@Component
@RequiredArgsConstructor
public class TransactionDtoSanitizer implements Sanitizer<TransactionDto> {
    private final CardDtoSanitizer cardDtoSanitizer;
    private final CardHolderDtoSanitizer cardHolderDtoSanitizer;

    /**
     * Sanitizes {@link TransactionDto}.
     *
     * @param transactionDto - must not be null.
     * @return {@link TransactionDto}.
     */
    @Override
    public TransactionDto sanitize(final TransactionDto transactionDto) {
        return new TransactionDto(
            transactionDto.invoice(),
            transactionDto.amount(),
            transactionDto.currency(),
            cardHolderDtoSanitizer.sanitize(transactionDto.cardHolder()),
            cardDtoSanitizer.sanitize(transactionDto.card())
        );
    }
}
