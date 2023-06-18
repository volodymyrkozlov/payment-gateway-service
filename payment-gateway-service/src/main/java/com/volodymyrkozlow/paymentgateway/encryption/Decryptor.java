package com.volodymyrkozlow.paymentgateway.encryption;

/**
 * Interface to provide a possibility to decrypt entities.
 *
 * @param <T> - entity to decrypt.
 */
interface Decryptor<T> {

    T decrypt(final T value);
}
