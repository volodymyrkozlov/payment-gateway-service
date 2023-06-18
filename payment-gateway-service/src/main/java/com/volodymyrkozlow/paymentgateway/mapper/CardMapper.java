package com.volodymyrkozlow.paymentgateway.mapper;

import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestCardDto;
import com.volodymyrkozlow.paymentgateway.entity.CardEntity;
import org.mapstruct.Mapper;

/**
 * Interface to provide a simplified way to map {@link CardEntity}.
 */
@Mapper
public interface CardMapper {

    /**
     * Maps {@link SubmitPaymentRequestCardDto} to {@link CardEntity}.
     *
     * @param card - must not be null.
     * @return {@link CardEntity}
     */
    CardEntity toEntity(final SubmitPaymentRequestCardDto card);
}
