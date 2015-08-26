package biz.podoliako.carwash.services.validation.impl;

import biz.podoliako.carwash.services.UserService;
import biz.podoliako.carwash.services.validation.LoginExist;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.SQLException;


public class LoginExistValidator implements ConstraintValidator<LoginExist, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(LoginExist loginExist) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            return !userService.isLoginExist(s);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
