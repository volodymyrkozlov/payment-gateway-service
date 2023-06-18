package com.volodymyrkozlow.paymentgateway.repository;

import com.volodymyrkozlow.paymentgateway.entity.CardHolderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * An interface to manage {@link CardHolderEntity}.
 */
public interface CardHolderRepository extends JpaRepository<CardHolderEntity, Long> {

    /**
     * Returns cardholder by  email.
     *
     * @param email - must not be null.
     * @return optional {@link CardHolderEntity}.
     */
    Optional<CardHolderEntity> findByEmail(final String email);
}
