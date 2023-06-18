package com.volodymyrkozlow.paymentgateway.exception;

import lombok.NoArgsConstructor;

/**
 * Exception which might occur on the server side.
 */
@NoArgsConstructor
public class InternalServerErrorException extends RuntimeException {

    public InternalServerErrorException(final Throwable throwable) {
        super(throwable);
    }
}
