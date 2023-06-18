package com.volodymyrkozlow.paymentgateway.dto;

/**
 * Data transfer object for card.
 */
public record CardDto(String pan,
                      String expiry) {
}
