package com.volodymyrkozlow.paymentgateway.encryption;

import com.volodymyrkozlow.paymentgateway.dto.TransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Decryptor to decrypt {@link TransactionDto}. Decryption output depends on {@link CardHolderDtoBase64Decryptor} and
 * {@link CardDtoBase64Decryptor} implementations.
 */
@Component
@RequiredArgsConstructor
public class TransactionDtoBase64Decryptor implements Decryptor<TransactionDto> {
    private final CardHolderDtoBase64Decryptor cardHolderDtoBase64Decryptor;
    private final CardDtoBase64Decryptor cardDtoBase64Decryptor;

    /**
     * Decrypts {@link TransactionDto}.
     *
     * @param transactionDto - must not be null.
     * @return {@link TransactionDto}.
     */
    @Override
    public TransactionDto decrypt(final TransactionDto transactionDto) {
        return new TransactionDto(
            transactionDto.invoice(),
            transactionDto.amount(),
            transactionDto.currency(),
            cardHolderDtoBase64Decryptor.decrypt(transactionDto.cardHolder()),
            cardDtoBase64Decryptor.decrypt(transactionDto.card())
        );
    }
}
