package biz.podoliako.carwash.dao.impl;

import biz.podoliako.carwash.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by bizon4ik on 27.05.15.
 */
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class DaoFactoryImpl implements DaoFactory {


    private  DaoCarWashOwner daoCarWashOwner;
    private CarWashDao carWashDao;
    private CategoryDao categoryDao;
    private ServiceDao serviceDao;

    @Autowired
    public DaoFactoryImpl(DaoCarWashOwner daoCarWashOwner,
                          CarWashDao carWashDao,
                          CategoryDao categoryDao,
                          ServiceDao serviceDao) {

        this.daoCarWashOwner = daoCarWashOwner;
        this.carWashDao = carWashDao;
        this.categoryDao = categoryDao;
        this.serviceDao = serviceDao;
    }

    @Override
    public DaoCarWashOwner getDaoCarWashOwner() {
        return daoCarWashOwner;
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


}
