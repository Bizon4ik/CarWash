package biz.podoliako.carwash.services.validation;

import biz.podoliako.carwash.services.validation.impl.NotNegativeValidaor;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotNegativeValidaor.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNegative {
    String message() default ("Поле не может быть отрицательным");
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
