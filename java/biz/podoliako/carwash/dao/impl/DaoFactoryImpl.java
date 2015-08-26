package biz.podoliako.carwash.dao.impl;

import biz.podoliako.carwash.dao.*;
import biz.podoliako.carwash.models.entity.CarBrand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;


@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class DaoFactoryImpl implements DaoFactory {



    private CarWashDao carWashDao;
    private CategoryDao categoryDao;
    private ServiceDao serviceDao;
    private UserDao userDao;
    private CarBrandDao carBrandDao;

    @Autowired
    public DaoFactoryImpl(
                          CarWashDao carWashDao,
                          CategoryDao categoryDao,
                          ServiceDao serviceDao,
                          UserDao userDao,
                          CarBrandDao carBrandDao) {


        this.carWashDao = carWashDao;
        this.categoryDao = categoryDao;
        this.serviceDao = serviceDao;
        this.userDao = userDao;
        this.carBrandDao = carBrandDao;
    }


    @Override
    public CarWashDao getCarWashDao() { return carWashDao; }

    @Override
    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    @Override
    public ServiceDao getServiceDao() {
        return serviceDao;
    }

    @Override
    public UserDao getUserDao() {    return userDao;   }

    @Override
    public CarBrandDao getCarBrandDao() {
        return carBrandDao;
    }
}
