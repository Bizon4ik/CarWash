package biz.podoliako.carwash.services.validation.impl;


import biz.podoliako.carwash.services.validation.NotNegative;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotNegativeValidaor implements ConstraintValidator<NotNegative, Integer> {
    @Override
    public void initialize(NotNegative constraintAnnotation) {

    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if ("".equals(value)) return  true;
        try {
            Integer val = Integer.valueOf(value);
            if (val < 0 ) return  false;

        }catch (Exception e) {
            return  false;
        }

        return  true;
    }
}
