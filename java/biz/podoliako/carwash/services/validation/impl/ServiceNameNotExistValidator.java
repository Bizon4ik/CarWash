package biz.podoliako.carwash.services.validation.impl;

import biz.podoliako.carwash.dao.DaoFactory;
import biz.podoliako.carwash.services.validation.ServiceNameNotExist;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.SQLException;


public class ServiceNameNotExistValidator implements ConstraintValidator<ServiceNameNotExist, String> {

    @Autowired
    DaoFactory daoFactory;

    @Override
    public void initialize(ServiceNameNotExist serviceNameNotExist) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;
        try {
            result = daoFactory.getServiceDao().isServiceNameExist(s.trim().toLowerCase());
        } catch (SQLException e) {
            result = true;
        }

        return !result;
    }
}
