package com.volodymyrkozlow.paymentgateway.service;

import com.volodymyrkozlow.paymentgateway.dto.TransactionDto;
import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestDto;
import com.volodymyrkozlow.paymentgateway.encryption.SubmitPaymentRequestDtoBase64Encryptor;
import com.volodymyrkozlow.paymentgateway.encryption.TransactionDtoBase64Decryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service to provide a possibility manage encrypted payment transactions in a database.
 */
@Service
@RequiredArgsConstructor
class EncryptedTransactionService implements TransactionService {
    private final SubmitPaymentRequestDtoBase64Encryptor submitPaymentRequestDtoBase64Encryptor;
    private final TransactionDtoBase64Decryptor transactionDtoBase64Decryptor;
    private final TransactionService transactionRepositoryService;

    /**
     * Returns a decrypted payment transaction by an invoice number.
     *
     * @param invoice - must not be null.
     * @return decrypted {@link TransactionDto}.
     */
    @Override
    @Transactional(readOnly = true)
    public TransactionDto findPayment(final Long invoice) {
        final var transactionDto = transactionRepositoryService.findPayment(invoice);

        return transactionDtoBase64Decryptor.decrypt(transactionDto);
    }

    /**
     * Encrypts input request and persists to a database.
     *
     * @param request - must not be null.
     * @return decrypted {@link TransactionDto}.
     */
    @Override
    @Transactional
    public TransactionDto submitPayment(final SubmitPaymentRequestDto request) {
        final var encryptedRequest = submitPaymentRequestDtoBase64Encryptor.encrypt(request);
        final var transactionDto = transactionRepositoryService.submitPayment(encryptedRequest);

        return transactionDtoBase64Decryptor.decrypt(transactionDto);
    }
}
