package com.volodymyrkozlow.paymentgateway.mapper;

import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestCardHolderDto;
import com.volodymyrkozlow.paymentgateway.entity.CardHolderEntity;
import org.mapstruct.Mapper;

/**
 * Interface to provide a simplified way to map {@link CardHolderEntity}.
 */
@Mapper
public interface CardHolderMapper {

    /**
     * Maps {@link SubmitPaymentRequestCardHolderDto} to {@link CardHolderEntity}.
     *
     * @param cardHolder - must not be null
     * @return {@link CardHolderEntity}
     */
    CardHolderEntity toEntity(final SubmitPaymentRequestCardHolderDto cardHolder);

}
