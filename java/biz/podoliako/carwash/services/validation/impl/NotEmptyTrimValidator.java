package biz.podoliako.carwash.services.validation.impl;


import biz.podoliako.carwash.services.validation.NotEmptyTrim;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyTrimValidator implements ConstraintValidator<NotEmptyTrim, String> {
    @Override
    public void initialize(NotEmptyTrim notEmptyTrim) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.trim().length() > 0;
    }
}
