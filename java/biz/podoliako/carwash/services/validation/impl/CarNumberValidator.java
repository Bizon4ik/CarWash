package biz.podoliako.carwash.services.validation.impl;


import biz.podoliako.carwash.services.validation.CarNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CarNumberValidator implements ConstraintValidator<CarNumber, String> {

    private final Pattern pRU = Pattern.compile("[\\p{IsCyrillic}0-9]{0,14}");
    private final Pattern pEU = Pattern.compile("[A-Za-z0-9]{0,14}");

    @Override
    public void initialize(CarNumber carNumber) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        Matcher matcherRU = pRU.matcher(s);
            if (!matcherRU.matches()) {
                Matcher matcherEU = pEU.matcher(s);
                return  matcherEU.matches();
            }

        return true;
    }
}
