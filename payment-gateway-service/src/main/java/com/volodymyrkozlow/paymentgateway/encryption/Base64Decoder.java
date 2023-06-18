package com.volodymyrkozlow.paymentgateway.encryption;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Encoder with Base64 decoder implementation.
 */
@Component
class Base64Decoder {
    private final Base64.Decoder decoder;

    Base64Decoder() {
        this.decoder = Base64.getDecoder();
    }

    String decode(final String value) {
        return new String(decoder.decode(value), StandardCharsets.UTF_8);
    }
}
