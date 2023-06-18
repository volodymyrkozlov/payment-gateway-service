package com.volodymyrkozlow.paymentgateway.validation;

import com.volodymyrkozlow.paymentgateway.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InvoiceNotExistsValidatorTest {
    @Mock
    private TransactionRepository transactionRepository;
    @InjectMocks
    private InvoiceNotExists.InvoiceNotExistsValidator invoiceNotExistsValidator;

    @Test
    void testIsValid() {
        //given
        final var invoice = 1234567L;
        when(transactionRepository.existsByInvoice(invoice)).thenReturn(false);

        //when
        final var isValid = invoiceNotExistsValidator.isValid(invoice, null);

        //then
        assertTrue(isValid);
    }

    @Test
    void testIsInvalid() {
        //given
        final var invoice = 1234567L;
        when(transactionRepository.existsByInvoice(invoice)).thenReturn(true);

        //when
        final var isValid = invoiceNotExistsValidator.isValid(invoice, null);

        //then
        assertFalse(isValid);
    }
}
