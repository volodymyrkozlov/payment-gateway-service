package com.volodymyrkozlow.paymentgateway.service;

import com.volodymyrkozlow.paymentgateway.dto.TransactionDto;
import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestDto;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface to provide a possibility to manage payment transactions.
 */
interface TransactionService {

    /**
     * Returns a payment transaction by an invoice number.
     *
     * @param invoice - must not be null.
     * @return {@link TransactionDto}.
     */
    @Transactional(readOnly = true)
    TransactionDto findPayment(final Long invoice);

    /**
     * Submits payment transaction to a storage.
     *
     * @param request - must not be null.
     * @return {@link TransactionDto}.
     */
    @Transactional
    TransactionDto submitPayment(final SubmitPaymentRequestDto request);
}
