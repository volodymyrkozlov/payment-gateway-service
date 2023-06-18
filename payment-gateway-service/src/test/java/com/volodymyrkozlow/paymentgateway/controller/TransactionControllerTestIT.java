package com.volodymyrkozlow.paymentgateway.controller;

import com.volodymyrkozlow.paymentgateway.dto.TransactionDto;
import com.volodymyrkozlow.paymentgateway.dto.TransactionSubmittedDto;
import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestCardDto;
import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestCardHolderDto;
import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestDto;
import com.volodymyrkozlow.paymentgateway.encryption.SubmitPaymentRequestDtoBase64Encryptor;
import com.volodymyrkozlow.paymentgateway.repository.CardHolderRepository;
import com.volodymyrkozlow.paymentgateway.repository.CardRepository;
import com.volodymyrkozlow.paymentgateway.repository.TransactionRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransactionControllerTestIT extends AbstractMvcTest {
    @Autowired
    private CardHolderRepository cardHolderRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private SubmitPaymentRequestDtoBase64Encryptor submitPaymentRequestDtoBase64Encryptor;

    @Test
    @SneakyThrows
    void testSubmitTransaction() {
        //given
        final var submitPaymentRequestCard = new SubmitPaymentRequestCardDto("4532151122758220", "0699", "789");
        final var submitPaymentRequestCardHolder = new SubmitPaymentRequestCardHolderDto("First Last", "email@domain.com");
        final var submitPaymentRequest = new SubmitPaymentRequestDto(1234567L, 1299L, "EUR", submitPaymentRequestCardHolder, submitPaymentRequestCard);

        //when
        final var transactionSubmittedDto = super.readJson(mvc.perform(post(TransactionController.ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.writeJson(submitPaymentRequest)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andReturn(), TransactionSubmittedDto.class);

        //then
        assertTrue(transactionSubmittedDto.approved());
        final var encryptedRequest = submitPaymentRequestDtoBase64Encryptor.encrypt(submitPaymentRequest);
        final var encryptedCard = encryptedRequest.card();
        final var encryptedCardHolder = encryptedRequest.cardHolder();

        final var optionalCardHolder = cardHolderRepository.findByEmail(encryptedCardHolder.email());
        assertTrue(optionalCardHolder.isPresent());
        final var cardHolderEntity = optionalCardHolder.get();
        assertEquals(encryptedCardHolder.name(), cardHolderEntity.getName());
        assertEquals(encryptedCardHolder.email(), cardHolderEntity.getEmail());

        final var optionalCard = cardRepository.findByPanAndCardHolderId(encryptedCard.pan(), cardHolderEntity.getId());
        assertTrue(optionalCard.isPresent());
        final var cardEntity = optionalCard.get();
        assertEquals(encryptedCard.pan(), cardEntity.getPan());
        assertEquals(encryptedCard.expiry(), cardEntity.getExpiry());

        final var optionalTransaction = transactionRepository.findByInvoice(encryptedRequest.invoice());
        assertTrue(optionalTransaction.isPresent());
        final var transactionEntity = optionalTransaction.get();
        assertEquals(encryptedRequest.invoice(), transactionEntity.getInvoice());
        assertEquals(encryptedRequest.amount(), transactionEntity.getAmount());
        assertEquals(encryptedRequest.currency(), transactionEntity.getCurrency());
    }

    @Test
    @SneakyThrows
    void findTransaction() {
        //given
        final var submitPaymentRequestCard = new SubmitPaymentRequestCardDto("4532151122758220", "0699", "789");
        final var submitPaymentRequestCardHolder = new SubmitPaymentRequestCardHolderDto("First Last", "email@domain.com");
        final var submitPaymentRequest = new SubmitPaymentRequestDto(1234568L, 1299L, "EUR", submitPaymentRequestCardHolder, submitPaymentRequestCard);

        mvc.perform(post(TransactionController.ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.writeJson(submitPaymentRequest)))
            .andDo(print())
            .andExpect(status().isCreated());

        //when
        final var transactionDto = super.readJson(mvc.perform(get(TransactionController.ROOT + "/" + submitPaymentRequest.invoice()))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn(), TransactionDto.class);


        //then
        assertEquals(submitPaymentRequest.invoice(), transactionDto.invoice());
        assertEquals(submitPaymentRequest.currency(), transactionDto.currency());
        assertEquals(submitPaymentRequest.amount(), transactionDto.amount());

        final var cardDto = transactionDto.card();
        assertEquals("************8220", cardDto.pan());
        assertEquals("****", cardDto.expiry());

        final var cardHolderDto = transactionDto.cardHolder();
        assertEquals("**********", cardHolderDto.name());
        assertEquals(submitPaymentRequestCardHolder.email(), cardHolderDto.email());
    }
}
