package com.volodymyrkozlow.paymentgateway.sanitation;

/**
 * Interface to provide a possibility to sanitize entities.
 *
 * @param <T> - entity to sanitize.
 */
public interface Sanitizer<T> {

    T sanitize(final T entity);
}
