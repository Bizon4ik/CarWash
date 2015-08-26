package biz.podoliako.carwash.services.validation.impl;


import biz.podoliako.carwash.services.CarBrandService;
import biz.podoliako.carwash.services.validation.CarBrandNotExist;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.SQLException;

public class CarBrandNotExistValidator implements ConstraintValidator<CarBrandNotExist, String> {

    @Autowired
    CarBrandService carBrandService;

    @Override
    public void initialize(CarBrandNotExist carBrandNotExist) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;
        try {
            result =  carBrandService.isCarBrandExist(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return !result;
    }
}
