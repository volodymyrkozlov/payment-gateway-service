package com.volodymyrkozlow.paymentgateway.service;

import com.volodymyrkozlow.paymentgateway.dto.TransactionDto;

/**
 * Interface to provide a possibility to publish payment transactions to external systems.
 */
interface ExternalAuditService {

    /**
     * Records submitted payment data to an external system.
     *
     * @param transactionDto - must not be null.
     */
    void publishTransaction(final TransactionDto transactionDto);
}
