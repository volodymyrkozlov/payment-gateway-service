package com.volodymyrkozlow.paymentgateway.repository;

import com.volodymyrkozlow.paymentgateway.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * An interface to manage {@link CardEntity}.
 */
public interface CardRepository extends JpaRepository<CardEntity, Long> {

    /**
     * Returns card by a card pan and cardholder id.
     *
     * @param cardPan      - must not be null.
     * @param cardHolderId - must not be null.
     * @return optional {@link CardEntity}.
     */
    Optional<CardEntity> findByPanAndCardHolderId(final String cardPan,
                                                  final Long cardHolderId);

}
