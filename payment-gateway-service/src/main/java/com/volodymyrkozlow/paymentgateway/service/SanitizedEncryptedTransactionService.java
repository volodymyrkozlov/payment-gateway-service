package com.volodymyrkozlow.paymentgateway.service;

import com.volodymyrkozlow.paymentgateway.dto.TransactionDto;
import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestDto;
import com.volodymyrkozlow.paymentgateway.sanitation.TransactionDtoSanitizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service to provide a possibility manage payment transactions in a database with a sanitized output.
 */
@Service
@RequiredArgsConstructor
class SanitizedEncryptedTransactionService implements TransactionService {
    private final TransactionService encryptedTransactionService;
    private final TransactionDtoSanitizer transactionDtoSanitizer;

    /**
     * Returns a sanitized payment transaction by an invoice number.
     *
     * @param invoice - must not be null.
     * @return decrypted and sanitized {@link TransactionDto}.
     */
    @Override
    @Transactional(readOnly = true)
    public TransactionDto findPayment(final Long invoice) {
        final var transactionDto = encryptedTransactionService.findPayment(invoice);

        return transactionDtoSanitizer.sanitize(transactionDto);
    }

    /**
     * Encrypts input request and persists to a database.
     *
     * @param request - must not be null.
     * @return decrypted and sanitized {@link TransactionDto}.
     */
    @Override
    @Transactional
    public TransactionDto submitPayment(final SubmitPaymentRequestDto request) {
        final var transactionDto = encryptedTransactionService.submitPayment(request);

        return transactionDtoSanitizer.sanitize(transactionDto);
    }
}
