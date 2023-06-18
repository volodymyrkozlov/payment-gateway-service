package com.volodymyrkozlow.paymentgateway.encryption;

/**
 * Interface to provide a possibility to encrypt entities.
 *
 * @param <T> - entity to encrypt.
 */
interface Encryptor<T> {

    T encrypt(final T value);
}
