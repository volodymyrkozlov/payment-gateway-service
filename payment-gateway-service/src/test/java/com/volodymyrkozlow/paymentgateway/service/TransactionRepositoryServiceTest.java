package com.volodymyrkozlow.paymentgateway.service;

import com.volodymyrkozlow.paymentgateway.dto.CardDto;
import com.volodymyrkozlow.paymentgateway.dto.CardHolderDto;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionRepositoryServiceTest {
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private CardHolderRepository cardHolderRepository;
    @Mock
    private CardRepository cardRepository;
    @Mock
    private TransactionMapper transactionMapper;
    @Mock
    private CardHolderMapper cardHolderMapper;
    @Mock
    private CardMapper cardMapper;
    @InjectMocks
    private TransactionRepositoryService transactionRepositoryService;

    @Test
    void testFindPayment() {
        //given
        final var invoice = 1234567L;

        final var transactionEntity = new TransactionEntity();
        transactionEntity.setId(1L);
        transactionEntity.setInvoice(invoice);
        transactionEntity.setAmount(1299L);
        transactionEntity.setCurrency("EUR");

        final var cardHolderEntity = new CardHolderEntity();
        cardHolderEntity.setId(1L);
        cardHolderEntity.setEmail("email@domain.com");
        cardHolderEntity.setName("First Last");

        final var cardEntity = new CardEntity();
        cardEntity.setId(1L);
        cardEntity.setExpiry("0624");
        cardEntity.setPan("789");

        final var cardHolderDto = new CardHolderDto(cardHolderEntity.getName(), cardHolderEntity.getEmail());
        final var cardDto = new CardDto(cardEntity.getPan(), cardEntity.getExpiry());
        final var expectedTransactionDto = new TransactionDto(invoice, transactionEntity.getAmount(), transactionEntity.getCurrency(), cardHolderDto, cardDto);

        when(transactionRepository.findByInvoice(invoice)).thenReturn(Optional.of(transactionEntity));
        when(transactionMapper.toDto(transactionEntity)).thenReturn(expectedTransactionDto);

        //when
        final var transactionDto = transactionRepositoryService.findPayment(invoice);

        //then
        assertEquals(expectedTransactionDto, transactionDto);
    }

    @Test
    void testSubmitPaymentWithNonExistingCardHolder() {
        //given
        final var submitPaymentRequestCard = new SubmitPaymentRequestCardDto("4200000000000001", "0624", "789");
        final var submitPaymentRequestCardHolder = new SubmitPaymentRequestCardHolderDto("First Last", "email@domain.com");
        final var submitPaymentRequest = new SubmitPaymentRequestDto(1234567L, 1299L, "EUR", submitPaymentRequestCardHolder, submitPaymentRequestCard);

        final var cardHolderEntity = new CardHolderEntity();
        cardHolderEntity.setEmail(submitPaymentRequestCardHolder.email());
        cardHolderEntity.setName(submitPaymentRequestCardHolder.name());

        final var cardEntity = new CardEntity();
        cardEntity.setExpiry(submitPaymentRequestCard.expiry());
        cardEntity.setPan(submitPaymentRequestCard.pan());
        cardEntity.setCardHolder(cardHolderEntity);

        final var transactionEntity = new TransactionEntity();
        transactionEntity.setInvoice(submitPaymentRequest.invoice());
        transactionEntity.setAmount(submitPaymentRequest.amount());
        transactionEntity.setCurrency(submitPaymentRequest.currency());
        transactionEntity.setCard(cardEntity);

        final var cardHolderDto = new CardHolderDto(cardHolderEntity.getName(), cardHolderEntity.getEmail());
        final var cardDto = new CardDto(cardEntity.getPan(), cardEntity.getExpiry());
        final var expectedTransactionDto = new TransactionDto(transactionEntity.getInvoice(), transactionEntity.getAmount(), transactionEntity.getCurrency(), cardHolderDto, cardDto);

        when(cardHolderRepository.findByEmail(submitPaymentRequestCardHolder.email())).thenReturn(Optional.empty());
        when(cardMapper.toEntity(submitPaymentRequestCard)).thenReturn(cardEntity);
        when(cardHolderMapper.toEntity(submitPaymentRequestCardHolder)).thenReturn(cardHolderEntity);
        when(transactionMapper.toEntity(submitPaymentRequest, cardEntity)).thenReturn(transactionEntity);
        when(transactionRepository.save(transactionEntity)).thenReturn(transactionEntity);
        when(transactionMapper.toDto(transactionEntity)).thenReturn(expectedTransactionDto);

        //when
        final var transactionDto = transactionRepositoryService.submitPayment(submitPaymentRequest);

        //then
        assertEquals(expectedTransactionDto, transactionDto);
    }

    @Test
    void testSubmitPaymentWithExistingCardHolderAndNonExistingCard() {
        //given
        final var submitPaymentRequestCard = new SubmitPaymentRequestCardDto("4200000000000001", "0624", "789");
        final var submitPaymentRequestCardHolder = new SubmitPaymentRequestCardHolderDto("First Last", "email@domain.com");
        final var submitPaymentRequest = new SubmitPaymentRequestDto(1234567L, 1299L, "EUR", submitPaymentRequestCardHolder, submitPaymentRequestCard);

        final var cardHolderEntity = new CardHolderEntity();
        cardHolderEntity.setEmail(submitPaymentRequestCardHolder.email());
        cardHolderEntity.setName(submitPaymentRequestCardHolder.name());

        final var cardEntity = new CardEntity();
        cardEntity.setExpiry(submitPaymentRequestCard.expiry());
        cardEntity.setPan(submitPaymentRequestCard.pan());
        cardEntity.setCardHolder(cardHolderEntity);

        final var transactionEntity = new TransactionEntity();
        transactionEntity.setInvoice(submitPaymentRequest.invoice());
        transactionEntity.setAmount(submitPaymentRequest.amount());
        transactionEntity.setCurrency(submitPaymentRequest.currency());
        transactionEntity.setCard(cardEntity);

        final var cardHolderDto = new CardHolderDto(cardHolderEntity.getName(), cardHolderEntity.getEmail());
        final var cardDto = new CardDto(cardEntity.getPan(), cardEntity.getExpiry());
        final var expectedTransactionDto = new TransactionDto(transactionEntity.getInvoice(), transactionEntity.getAmount(), transactionEntity.getCurrency(), cardHolderDto, cardDto);

        when(cardHolderRepository.findByEmail(submitPaymentRequestCardHolder.email())).thenReturn(Optional.of(cardHolderEntity));
        when(cardRepository.findByPanAndCardHolderId(submitPaymentRequestCard.pan(), cardHolderEntity.getId())).thenReturn(Optional.empty());
        when(cardMapper.toEntity(submitPaymentRequestCard)).thenReturn(cardEntity);
        when(cardRepository.save(cardEntity)).thenReturn(cardEntity);
        when(transactionMapper.toEntity(submitPaymentRequest, cardEntity)).thenReturn(transactionEntity);
        when(transactionRepository.save(transactionEntity)).thenReturn(transactionEntity);
        when(transactionMapper.toDto(transactionEntity)).thenReturn(expectedTransactionDto);

        //when
        final var transactionDto = transactionRepositoryService.submitPayment(submitPaymentRequest);

        //then
        assertEquals(expectedTransactionDto, transactionDto);
    }

    @Test
    void testSubmitPaymentWithExistingCardHolderAndExistingCard() {
        //given
        final var submitPaymentRequestCard = new SubmitPaymentRequestCardDto("4200000000000001", "0624", "789");
        final var submitPaymentRequestCardHolder = new SubmitPaymentRequestCardHolderDto("First Last", "email@domain.com");
        final var submitPaymentRequest = new SubmitPaymentRequestDto(1234567L, 1299L, "EUR", submitPaymentRequestCardHolder, submitPaymentRequestCard);

        final var cardHolderEntity = new CardHolderEntity();
        cardHolderEntity.setEmail(submitPaymentRequestCardHolder.email());

        final var cardEntity = new CardEntity();
        cardEntity.setExpiry(submitPaymentRequestCard.expiry());
        cardEntity.setPan(submitPaymentRequestCard.pan());
        cardEntity.setCardHolder(cardHolderEntity);

        final var transactionEntity = new TransactionEntity();
        transactionEntity.setInvoice(submitPaymentRequest.invoice());
        transactionEntity.setAmount(submitPaymentRequest.amount());
        transactionEntity.setCurrency(submitPaymentRequest.currency());
        transactionEntity.setCard(cardEntity);

        final var cardHolderDto = new CardHolderDto(cardHolderEntity.getName(), cardHolderEntity.getEmail());
        final var cardDto = new CardDto(cardEntity.getPan(), cardEntity.getExpiry());
        final var expectedTransactionDto = new TransactionDto(transactionEntity.getInvoice(), transactionEntity.getAmount(), transactionEntity.getCurrency(), cardHolderDto, cardDto);

        when(cardHolderRepository.findByEmail(submitPaymentRequestCardHolder.email())).thenReturn(Optional.of(cardHolderEntity));
        when(cardRepository.findByPanAndCardHolderId(submitPaymentRequestCard.pan(), cardEntity.getId())).thenReturn(Optional.of(cardEntity));
        when(transactionMapper.toEntity(submitPaymentRequest, cardEntity)).thenReturn(transactionEntity);
        when(transactionRepository.save(transactionEntity)).thenReturn(transactionEntity);
        when(transactionMapper.toDto(transactionEntity)).thenReturn(expectedTransactionDto);

        //when
        final var transactionDto = transactionRepositoryService.submitPayment(submitPaymentRequest);

        //then
        assertEquals(expectedTransactionDto, transactionDto);
    }
}
