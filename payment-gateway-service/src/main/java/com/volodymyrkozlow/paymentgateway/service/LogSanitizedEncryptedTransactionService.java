package com.volodymyrkozlow.paymentgateway.service;

import com.volodymyrkozlow.paymentgateway.dto.TransactionDto;
import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service to provide a possibility manage payment transactions in a database with a sanitized output. This service
 * performs external service payments streaming.
 */
@Service
@RequiredArgsConstructor
public class LogSanitizedEncryptedTransactionService implements TransactionService {
    private final TransactionService sanitizedEncryptedTransactionService;
    private final ExternalAuditService fileExternalAuditService;

    /**
     * Returns a sanitized payment transaction by an invoice number.
     *
     * @param invoice - must not be null.
     * @return decrypted and sanitized {@link TransactionDto}.
     */
    @Override
    @Transactional(readOnly = true)
    public TransactionDto findPayment(final Long invoice) {
        return sanitizedEncryptedTransactionService.findPayment(invoice);
    }

    /**
     * Encrypts input request and persists to a database. Publishes successfully submitted payment data to
     * an external system.
     *
     * @param request - must not be null.
     * @return decrypted and sanitized {@link TransactionDto}.
     */
    @Override
    @Transactional
    public TransactionDto submitPayment(final SubmitPaymentRequestDto request) {
        final var transactionDto = sanitizedEncryptedTransactionService.submitPayment(request);
        fileExternalAuditService.publishTransaction(transactionDto);

        return transactionDto;
    }
}
