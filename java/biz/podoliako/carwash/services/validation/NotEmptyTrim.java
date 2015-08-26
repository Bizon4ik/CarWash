package biz.podoliako.carwash.services.validation;


import biz.podoliako.carwash.services.validation.impl.NotEmptyTrimValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = NotEmptyTrimValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmptyTrim {
    String message() default ("Поле не может быть пустым");
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
