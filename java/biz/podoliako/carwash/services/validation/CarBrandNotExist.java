package biz.podoliako.carwash.services.validation;

import biz.podoliako.carwash.services.validation.impl.CarBrandNotExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CarBrandNotExistValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CarBrandNotExist {
    String message() default ("Автобред уже существует");
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
