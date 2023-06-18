package com.volodymyrkozlow.paymentgateway.mapper;

import com.volodymyrkozlow.paymentgateway.dto.TransactionDto;
import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestDto;
import com.volodymyrkozlow.paymentgateway.entity.CardEntity;
import com.volodymyrkozlow.paymentgateway.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Interface to provide a simplified way to map {@link TransactionEntity}.
 */
@Mapper
public interface TransactionMapper {

    /**
     * Maps {@link SubmitPaymentRequestDto} and {@link CardEntity} to a {@link TransactionEntity}.
     *
     * @param request - must not be null.
     * @param card    - must not be null.
     * @return {@link TransactionEntity}.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "card.cardHolder", source = "card.cardHolder")
    @Mapping(target = "card.id", source = "card.id")
    @Mapping(target = "card.pan", source = "card.pan")
    @Mapping(target = "card.expiry", source = "card.expiry")
    TransactionEntity toEntity(final SubmitPaymentRequestDto request,
                               final CardEntity card);

    /**
     * Maps {@link TransactionEntity} to a {@link TransactionDto}.
     *
     * @param entity - must not be null.
     * @return {@link TransactionDto}.
     */
    @Mapping(target = "cardHolder", source = "card.cardHolder")
    TransactionDto toDto(final TransactionEntity entity);
}
