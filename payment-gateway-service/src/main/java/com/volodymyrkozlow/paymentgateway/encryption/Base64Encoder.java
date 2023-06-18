package com.volodymyrkozlow.paymentgateway.encryption;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Encoder with Base64 encoder implementation.
 */
@Component
class Base64Encoder {
    private final Base64.Encoder encoder;

    Base64Encoder() {
        this.encoder = Base64.getEncoder();
    }

    String encrypt(final String value) {
        return encoder.encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }
}
