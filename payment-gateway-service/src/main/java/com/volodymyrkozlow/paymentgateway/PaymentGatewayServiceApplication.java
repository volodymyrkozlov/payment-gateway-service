package com.volodymyrkozlow.paymentgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Payment gateway application.
 */
@SpringBootApplication
public class PaymentGatewayServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(PaymentGatewayServiceApplication.class, args);
    }

}
