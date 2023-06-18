package com.volodymyrkozlow.paymentgateway.validation;

import com.volodymyrkozlow.paymentgateway.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validator to validate if request invoice does not exist.
 */
@Constraint(validatedBy = InvoiceNotExists.InvoiceNotExistsValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InvoiceNotExists {
    String message() default "invoice already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @RequiredArgsConstructor
    class InvoiceNotExistsValidator implements ConstraintValidator<InvoiceNotExists, Long> {
        private final TransactionRepository transactionRepository;

        @Override
        public boolean isValid(final Long value,
                               final ConstraintValidatorContext context) {
            return !transactionRepository.existsByInvoice(value);
        }
    }
}
