package biz.podoliako.carwash.services.validation;


import biz.podoliako.carwash.services.validation.impl.CarNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CarNumberValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CarNumber {
    String message() default ("Не верный формат автомобильного номера");
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
