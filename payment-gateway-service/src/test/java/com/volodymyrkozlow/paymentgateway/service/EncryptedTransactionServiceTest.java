package com.volodymyrkozlow.paymentgateway.service;

import com.volodymyrkozlow.paymentgateway.dto.CardDto;
import com.volodymyrkozlow.paymentgateway.dto.CardHolderDto;
import com.volodymyrkozlow.paymentgateway.dto.TransactionDto;
import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestCardDto;
import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestCardHolderDto;
import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestDto;
import com.volodymyrkozlow.paymentgateway.encryption.SubmitPaymentRequestDtoBase64Encryptor;
import com.volodymyrkozlow.paymentgateway.encryption.TransactionDtoBase64Decryptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EncryptedTransactionServiceTest {
    @Mock
    private SubmitPaymentRequestDtoBase64Encryptor submitPaymentRequestDtoBase64Encryptor;
    @Mock
    private TransactionDtoBase64Decryptor transactionDtoBase64Decryptor;
    @Mock
    private TransactionService transactionRepositoryService;
    @InjectMocks
    private EncryptedTransactionService encryptedTransactionService;

    @Test
    void test() {
        final var list = List.of(1,2,3,4,5);
        final var list2 = List.of(1,2);

        List<Integer> collect1 = list.stream()
            .map(s -> {
                if (list2.contains(s)) {
                    return 1;
                }
                return 0;
            }).collect(Collectors.toList());

        List<List<Integer>> collect = list2
            .stream()
            .map(id2 -> {
                return list.stream()
                    .map(id1 -> {
                        if (id2.equals(id1)) {
                            return 0;
                        }
                        return 1;
                    }).collect(Collectors.toList());
            }).collect(Collectors.toList());
        System.out.println();
    }

    @Test
    void testFindPayment() {
        //given
        final var invoice = 1234567L;

        final var cardHolderDto = new CardHolderDto("First Last", "email@domain.com");
        final var cardDto = new CardDto("4200000000000001", "0624");
        final var expectedTransactionDto = new TransactionDto(invoice, 1299L, "EUR", cardHolderDto, cardDto);

        when(transactionRepositoryService.findPayment(invoice)).thenReturn(expectedTransactionDto);
        when(transactionDtoBase64Decryptor.decrypt(expectedTransactionDto)).thenReturn(expectedTransactionDto);

        //when
        final var transactionDto = encryptedTransactionService.findPayment(invoice);

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

        when(transactionRepositoryService.submitPayment(submitPaymentRequest)).thenReturn(expectedTransactionDto);
        when(submitPaymentRequestDtoBase64Encryptor.encrypt(submitPaymentRequest)).thenReturn(submitPaymentRequest);
        when(transactionDtoBase64Decryptor.decrypt(expectedTransactionDto)).thenReturn(expectedTransactionDto);

        //when
        final var transactionDto = encryptedTransactionService.submitPayment(submitPaymentRequest);

        //then
        assertEquals(expectedTransactionDto, transactionDto);
    }
}
