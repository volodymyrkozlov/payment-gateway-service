package com.volodymyrkozlow.paymentgateway.config;

import com.volodymyrkozlow.paymentgateway.property.PaymentGatewayProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Configuration for payment gateway application.
 */
@Configuration
@Import(PaymentGatewayProperties.class)
public class PaymentGatewayAppConfig {
}
