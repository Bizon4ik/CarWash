package biz.podoliako.carwash.dao;

import biz.podoliako.carwash.dao.pojo.CarWash;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by bizon4ik on 15.06.15.
 */
public interface CarWashDao {
    public final static Integer NAME_MAX_LENGTH = 50;
    public final static Integer ADDRESS_MAX_LENGTH = 200;
    public final static Integer PHONE_NUMBER_MAX_LENGTH = 20;


    public void addCarWash(CarWash carWash) throws SQLException;

    public void deleteCarWash(Integer carWashId) throws SQLException;

    public void modifyCarWash();

    public boolean isCarWashNameExist(String name) throws SQLException;

    public CarWash selectCarWash(Integer id) throws SQLException;

    public List<CarWash> selectAllCarWash(Integer ownerId) throws SQLException;
}
