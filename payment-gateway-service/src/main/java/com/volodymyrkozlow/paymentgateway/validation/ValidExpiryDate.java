package com.volodymyrkozlow.paymentgateway.validation;

import com.volodymyrkozlow.paymentgateway.property.PaymentGatewayProperties;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Validator to validate if request card expiry date is not expired.
 */
@Constraint(validatedBy = ValidExpiryDate.ValidExpiryDateValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidExpiryDate {
    String message() default "payment card is expired";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class ValidExpiryDateValidator implements ConstraintValidator<ValidExpiryDate, String> {
        private final ZoneId zoneId;
        private final DateTimeFormatter dateTimeFormatter;

        public ValidExpiryDateValidator(final PaymentGatewayProperties paymentGatewayProperties) {
            final var card = paymentGatewayProperties.getCard();
            final var zoneId = ZoneId.of(card.getExpiryZoneId());
            this.zoneId = zoneId;
            this.dateTimeFormatter = DateTimeFormatter.ofPattern(card.getExpiryFormat()).withZone(zoneId);
        }

        @Override
        public boolean isValid(final String expiry,
                               final ConstraintValidatorContext context) {
            try {
                final var expiryYearMonth = YearMonth.parse(expiry, dateTimeFormatter);

                return expiryYearMonth.isAfter(YearMonth.now(zoneId));
            } catch (final DateTimeParseException ex) {
                return false;
            }
        }
    }
}
