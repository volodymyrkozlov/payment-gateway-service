package com.volodymyrkozlow.paymentgateway.service;

import com.volodymyrkozlow.paymentgateway.dto.CardDto;
import com.volodymyrkozlow.paymentgateway.dto.CardHolderDto;
import com.volodymyrkozlow.paymentgateway.dto.TransactionDto;
import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestCardDto;
import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestCardHolderDto;
import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LogSanitizedEncryptedTransactionServiceTest {
    @Mock
    private TransactionService encryptedTransactionService;
    @Mock
    private ExternalAuditService externalAuditService;
    @InjectMocks
    private LogSanitizedEncryptedTransactionService logSanitizedEncryptedTransactionService;

    @Test
    void testFindPayment() {
        //given
        final var invoice = 1234567L;

        final var cardHolderDto = new CardHolderDto("First Last", "email@domain.com");
        final var cardDto = new CardDto("4200000000000001", "0624");
        final var expectedTransactionDto = new TransactionDto(invoice, 1299L, "EUR", cardHolderDto, cardDto);

        when(encryptedTransactionService.findPayment(invoice)).thenReturn(expectedTransactionDto);

        //when
        final var transactionDto = logSanitizedEncryptedTransactionService.findPayment(invoice);

        //then
        assertEquals(expectedTransactionDto, transactionDto);
    }

    @Test
    void testSubmitPayment() {
        //given
        final var submitPaymentRequestCard = new SubmitPaymentRequestCardDto("4200000000000001", "0624", "789");
        final var submitPaymentRequestCardHolder = new SubmitPaymentRequestCardHolderDto("First Last", "email@domain.com");
        final var submitPaymentRequest = new SubmitPaymentRequestDto(1234567L, 1299L, "EUR", submitPaymentRequestCardHolder, submitPaymentRequestCard);

        final var cardHolderDto = new CardHolderDto(submitPaymentRequestCardHolder.name(), submitPaymentRequestCardHolder.email());
        final var cardDto = new CardDto(submitPaymentRequestCard.pan(), submitPaymentRequestCard.expiry());
        final var expectedTransactionDto = new TransactionDto(submitPaymentRequest.invoice(), submitPaymentRequest.amount(), submitPaymentRequest.currency(), cardHolderDto, cardDto);

        when(encryptedTransactionService.submitPayment(submitPaymentRequest)).thenReturn(expectedTransactionDto);
        doNothing().when(externalAuditService).publishTransaction(expectedTransactionDto);

        //when
        final var transactionDto = logSanitizedEncryptedTransactionService.submitPayment(submitPaymentRequest);

        //then
        assertEquals(expectedTransactionDto, transactionDto);
    }
}
