package com.volodymyrkozlow.paymentgateway.repository;

import com.volodymyrkozlow.paymentgateway.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * An interface to manage {@link TransactionEntity}.
 */
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    /**
     * Returns transaction by an invoice.
     *
     * @param invoice - must not be null.
     * @return optional {@link TransactionEntity}.
     */
    Optional<TransactionEntity> findByInvoice(final Long invoice);

    /**
     * Checks whether the transaction exists by an invoice.
     * @param invoice - must not be null.
     * @return true if exists, false if not.
     */
    boolean existsByInvoice(final Long invoice);

}
