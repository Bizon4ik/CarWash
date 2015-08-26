package biz.podoliako.carwash.services.validation;

import biz.podoliako.carwash.services.validation.impl.BigDecimalValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BigDecimalValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BigDecimalString {
    String message() default ("Не верный формат числа");
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
