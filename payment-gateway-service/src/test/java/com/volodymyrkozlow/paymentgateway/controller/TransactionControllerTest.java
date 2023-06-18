package com.volodymyrkozlow.paymentgateway.controller;

import com.volodymyrkozlow.paymentgateway.dto.CardDto;
import com.volodymyrkozlow.paymentgateway.dto.CardHolderDto;
import com.volodymyrkozlow.paymentgateway.dto.TransactionDto;
import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestCardDto;
import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestCardHolderDto;
import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestDto;
import com.volodymyrkozlow.paymentgateway.service.LogSanitizedEncryptedTransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {
    @Mock
    private LogSanitizedEncryptedTransactionService transactionService;
    @InjectMocks
    private TransactionController transactionController;

    @Test
    void testFindTransaction() {
        //given
        final var invoice = 1234567L;
        final var cardHolderDto = new CardHolderDto("First Last", "email@domain.com");
        final var cardDto = new CardDto("4200000000000001", "0624");
        final var expectedTransactionDto = new TransactionDto(invoice, 1299L, "EUR", cardHolderDto, cardDto);

        when(transactionService.findPayment(invoice)).thenReturn(expectedTransactionDto);

        //when
        final var transactionDto = transactionController.findTransaction(invoice);

        //then
        assertEquals(expectedTransactionDto, transactionDto);
    }

    @Test
    void testSubmitTransaction() {
        //given
        final var submitPaymentRequestCard = new SubmitPaymentRequestCardDto("4200000000000001", "0624", "789");
        final var submitPaymentRequestCardHolder = new SubmitPaymentRequestCardHolderDto("First Last", "email@domain.com");
        final var submitPaymentRequest = new SubmitPaymentRequestDto(1234567L, 1299L, "EUR", submitPaymentRequestCardHolder, submitPaymentRequestCard);

        final var cardHolderDto = new CardHolderDto(submitPaymentRequestCardHolder.name(), submitPaymentRequestCardHolder.email());
        final var cardDto = new CardDto(submitPaymentRequestCard.pan(), submitPaymentRequestCard.expiry());
        final var transactionDto = new TransactionDto(submitPaymentRequest.invoice(), submitPaymentRequest.amount(), submitPaymentRequest.currency(), cardHolderDto, cardDto);

        when(transactionService.submitPayment(submitPaymentRequest)).thenReturn(transactionDto);

        //when
        final var transactionSubmittedDto = transactionController.submitTransaction(submitPaymentRequest);

        //then
        assertTrue(transactionSubmittedDto.approved());
    }
}
