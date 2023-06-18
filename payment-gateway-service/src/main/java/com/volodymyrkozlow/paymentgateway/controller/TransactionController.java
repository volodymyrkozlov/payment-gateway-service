package com.volodymyrkozlow.paymentgateway.controller;

import com.volodymyrkozlow.paymentgateway.dto.TransactionDto;
import com.volodymyrkozlow.paymentgateway.dto.TransactionSubmittedDto;
import com.volodymyrkozlow.paymentgateway.dto.request.SubmitPaymentRequestDto;
import com.volodymyrkozlow.paymentgateway.service.LogSanitizedEncryptedTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Controller to manage transactions thought REST API.
 */
@Validated
@RequestMapping(TransactionController.ROOT)
@RestController
@RequiredArgsConstructor
public class TransactionController {
    public static final String ROOT = "/api/v1/transactions";
    private final LogSanitizedEncryptedTransactionService logSanitizedEncryptedTransactionService;

    /**
     * Returns a payment transaction by an invoice number.
     *
     * @param invoice - must not be null.
     * @return {@link TransactionDto}.
     */
    @GetMapping("/{invoice}")
    public TransactionDto findTransaction(@PathVariable(name = "invoice") final Long invoice) {
        return logSanitizedEncryptedTransactionService.findPayment(invoice);
    }


    /**
     * Submits payment transaction to a storage.
     *
     * @param request - must not be null.
     * @return {@link TransactionSubmittedDto}.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TransactionSubmittedDto submitTransaction(@RequestBody @Valid final SubmitPaymentRequestDto request) {
        logSanitizedEncryptedTransactionService.submitPayment(request);
        return new TransactionSubmittedDto(true);
    }
}