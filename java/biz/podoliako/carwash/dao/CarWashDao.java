package biz.podoliako.carwash.dao;

import biz.podoliako.carwash.models.entity.CarWash;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;


public interface CarWashDao {
    public final static Integer NAME_MAX_LENGTH = 50;
    public final static Integer ADDRESS_MAX_LENGTH = 200;
    public final static Integer PHONE_NUMBER_MAX_LENGTH = 20;
    public final static String CAR_WASH_TABLE = "car_wash";


    public void addCarWash(CarWash carWash) throws SQLException;

    public void deleteCarWash(Integer carWashId) throws SQLException;

    public void modifyCarWash();

    public boolean isCarWashNameExist(String name) throws SQLException;

    public CarWash selectCarWash(Integer id);

    public List<CarWash> selectAllCarWash(Integer ownerId) throws SQLException;

}
