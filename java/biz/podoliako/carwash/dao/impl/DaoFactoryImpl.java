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
    private OrderDao orderDao;
    private ClientCarDao clientCarDao;
    private StatisticDao statisticDao;

    @Autowired
    public DaoFactoryImpl(
                          CarWashDao carWashDao,
                          CategoryDao categoryDao,
                          ServiceDao serviceDao,
                          UserDao userDao,
                          CarBrandDao carBrandDao,
                          OrderDao orderDao,
                          ClientCarDao clientCarDao,
                          StatisticDao statisticDao) {


        this.carWashDao = carWashDao;
        this.categoryDao = categoryDao;
        this.serviceDao = serviceDao;
        this.userDao = userDao;
        this.carBrandDao = carBrandDao;
        this.orderDao = orderDao;
        this.clientCarDao = clientCarDao;
        this.statisticDao = statisticDao;
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

    @Override
    public OrderDao getOrderDao() {
        return  orderDao;
    }

    @Override
    public ClientCarDao getClientCarDao() {
        return clientCarDao;
    }

    @Override
    public StatisticDao getStatisticDao() {
        return statisticDao;
    }
}
