package biz.podoliako.carwash.services.validation.impl;


import biz.podoliako.carwash.services.validation.BigDecimalString;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class BigDecimalValidator implements ConstraintValidator<BigDecimalString, String> {

    @Override
    public void initialize(BigDecimalString bigDecimalString) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            new BigDecimal(s);
            return true;
        }catch (Exception e) {
            return  false;
        }

    }
}
