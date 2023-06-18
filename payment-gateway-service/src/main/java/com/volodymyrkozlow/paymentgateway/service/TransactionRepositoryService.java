package com.volodymyrkozlow.paymentgateway.service;

import com.volodymyrkozlow.paymentgateway.dto.TransactionDto;
import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestCardDto;
import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestCardHolderDto;
import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestDto;
import com.volodymyrkozlow.paymentgateway.entity.CardEntity;
import com.volodymyrkozlow.paymentgateway.entity.CardHolderEntity;
import com.volodymyrkozlow.paymentgateway.entity.TransactionEntity;
import com.volodymyrkozlow.paymentgateway.mapper.CardHolderMapper;
import com.volodymyrkozlow.paymentgateway.mapper.CardMapper;
import com.volodymyrkozlow.paymentgateway.mapper.TransactionMapper;
import com.volodymyrkozlow.paymentgateway.repository.CardHolderRepository;
import com.volodymyrkozlow.paymentgateway.repository.CardRepository;
import com.volodymyrkozlow.paymentgateway.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

/**
 * Service to provide a possibility manage payment transactions in a database.
 */
@Component
@RequiredArgsConstructor
class TransactionRepositoryService implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final CardHolderRepository cardHolderRepository;
    private final CardRepository cardRepository;
    private final TransactionMapper transactionMapper;
    private final CardHolderMapper cardHolderMapper;
    private final CardMapper cardMapper;

    /**
     * Returns a payment transaction by an invoice number.
     *
     * @param invoice - must not be null.
     * @return {@link TransactionDto}.
     */
    @Override
    @Transactional(readOnly = true)
    public TransactionDto findPayment(final Long invoice) {
        final var transactionEntity = transactionRepository.findByInvoice(invoice)
            .orElseThrow(
                () -> new EntityNotFoundException(String.format("Transaction with invoice:[%d] is not found", invoice))
            );

        return transactionMapper.toDto(transactionEntity);
    }

    /**
     * Submits payment transaction represented as {@link TransactionEntity} to a database.
     * Creates a new {@link CardHolderEntity} in case it doesn't exist in the database.
     * Creates a new {@link CardEntity} in case it doesn't exist in a database.
     *
     * @param request - must not be null.
     * @return {@link TransactionDto}.
     */
    @Override
    @Transactional
    public TransactionDto submitPayment(final SubmitPaymentRequestDto request) {
        final var cardHolderDto = request.cardHolder();
        final var requestCardDto = request.card();

        return cardHolderRepository.findByEmail(cardHolderDto.email())
            .map(cardHolder -> cardRepository.findByPanAndCardHolderId(requestCardDto.pan(), cardHolder.getId())
                .map(card -> this.saveTransactionEntity(request, card))
                .orElseGet(() -> {
                    final var cardEntity = this.saveCardEntity(cardHolder, requestCardDto);

                    return this.saveTransactionEntity(request, cardEntity);
                }))
            .orElseGet(() -> {
                final var cardEntity = cardMapper.toEntity(requestCardDto);
                this.saveCardHolderEntity(cardHolderDto, cardEntity);

                return this.saveTransactionEntity(request, cardEntity);
            });
    }

    private CardEntity saveCardEntity(final CardHolderEntity cardHolderEntity,
                                      final SubmitPaymentRequestCardDto requestCardDto) {
        final var cardEntity = cardMapper.toEntity(requestCardDto);
        cardHolderEntity.addCard(cardEntity);

        return cardRepository.save(cardEntity);
    }

    private void saveCardHolderEntity(final SubmitPaymentRequestCardHolderDto requestCardHolderDto,
                                      final CardEntity cardEntity) {
        final var cardHolderEntity = cardHolderMapper.toEntity(requestCardHolderDto);
        cardHolderEntity.addCard(cardEntity);

        cardHolderRepository.save(cardHolderEntity);
    }

    private TransactionDto saveTransactionEntity(final SubmitPaymentRequestDto request,
                                                 final CardEntity cardEntity) {
        final var transactionEntity = transactionMapper.toEntity(request, cardEntity);
        final var savedTransactionEntity = transactionRepository.save(transactionEntity);

        return transactionMapper.toDto(savedTransactionEntity);
    }
}
