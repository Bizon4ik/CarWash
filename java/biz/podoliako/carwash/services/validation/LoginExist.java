package biz.podoliako.carwash.services.validation;


import biz.podoliako.carwash.services.validation.impl.LoginExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LoginExistValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginExist {
    String message() default ("Логин уже существует");
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
