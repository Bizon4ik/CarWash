package biz.podoliako.carwash.dao;

import biz.podoliako.carwash.dao.pojo.CarWashOwner;

import java.sql.SQLException;

/**
 * Created by bizon4ik on 27.05.15.
 */
public interface DaoCarWashOwner {

    public CarWashOwner selectCarWashOwner(String login, String password) throws SQLException;

    public String str();
}
