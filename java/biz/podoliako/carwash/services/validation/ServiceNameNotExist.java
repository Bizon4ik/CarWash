package biz.podoliako.carwash.services.validation;


import biz.podoliako.carwash.services.validation.impl.ServiceNameNotExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ServiceNameNotExistValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceNameNotExist {
    String message() default ("Сервис уже существует");
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}


