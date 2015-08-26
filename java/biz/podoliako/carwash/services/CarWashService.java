package biz.podoliako.carwash.services;

import biz.podoliako.carwash.models.entity.CarWash;
import biz.podoliako.carwash.models.pojo.CarWashFormErrors;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface CarWashService {
    public void addCarWash(CarWash carWash) throws SQLException;

    public List<CarWash> getAllCarWashes(Integer ownerId) throws SQLException;

    public void deleteCarWash(String id) throws SQLException;

    public void modifyCarWash();

    public CarWashFormErrors validateCarWashParam(CarWash carWash) throws SQLException;

    void setShiftTime(CarWash carWash, String startHours, String startMin, String finishHours, String finishMin) throws ParseException;

    CarWash selectCarWash(Integer carWashId) throws SQLException, NamingException;
}
