package com.volodymyrkozlow.paymentgateway.property;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Properties class to inject payment gateway properties from configuration file.
 */
@Validated
@Getter
@ToString
@EqualsAndHashCode
@ConfigurationProperties(prefix = "payment-gateway")
public class PaymentGatewayProperties {
    @NotBlank
    private String sanitizerCharacter;
    @NotBlank
    private String logFilePath;

    public void setLogFilePath(final String logFilePath) {
        this.logFilePath = logFilePath;
    }

    public void setSanitizerCharacter(final String sanitizerCharacter) {
        this.sanitizerCharacter = sanitizerCharacter;
    }

    @Valid
    @Setter
    @NotNull
    private PaymentCardProperty card;

    @Getter
    @ToString
    @EqualsAndHashCode
    public static class PaymentCardProperty {
        private String expiryFormat;
        private String expiryZoneId;

        public void setExpiryFormat(final String expiryFormat) {
            this.expiryFormat = expiryFormat;
        }

        public void setExpiryZoneId(final String expiryZoneId) {
            this.expiryZoneId = expiryZoneId;
        }
    }
}
