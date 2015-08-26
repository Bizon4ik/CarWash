package biz.podoliako.carwash.services.impl;

import biz.podoliako.carwash.dao.CarWashDao;
import biz.podoliako.carwash.dao.DaoFactory;
import biz.podoliako.carwash.models.entity.CarWash;
import biz.podoliako.carwash.services.CarWashService;
import biz.podoliako.carwash.models.pojo.CarWashFormErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("CarWashService")
public class CarWashServiceImpl implements CarWashService {

    private DaoFactory daoFactory;

    @Autowired
    public CarWashServiceImpl(DaoFactory daoFactory){
        this.daoFactory = daoFactory;
    }

    @Override
    public void addCarWash(CarWash carWash) throws SQLException {
        carWash.setDateOfCreation(new Date());
        daoFactory.getCarWashDao().addCarWash(carWash);

    }

    @Override
    public List<CarWash> getAllCarWashes(Integer ownerId) throws SQLException {
        return daoFactory.getCarWashDao().selectAllCarWash(ownerId);
    }

    @Override
    public void deleteCarWash(String id) throws SQLException {
        Integer carWashId = Integer.valueOf(id);
        daoFactory.getCarWashDao().deleteCarWash(carWashId);
    }

    @Override
    public void modifyCarWash() {

    }

    @Override
    public CarWashFormErrors validateCarWashParam(CarWash carWash) throws SQLException {
        CarWashFormErrors er = new CarWashFormErrors();

        er.setNameErrorMsg(validateCarWashName(carWash.getName()));
        er.setAddressErrorMsg(validateCarWashAddress(carWash.getAddress()));
        er.setPhoneNumberErrorMsg(validateCarWashPhoneNumber(carWash.getPhoneNumber()));

        return  er;
    }

    @Override
    public void setShiftTime(CarWash carWash, String startHours, String startMin, String finishHours, String finishMin) throws ParseException {
        DateFormat df = new SimpleDateFormat("HH:mm");

        Date startShift = df.parse(startHours.trim() + ":" + startMin.trim());
        Date finishShift = df.parse(finishHours.trim() + ":" + finishMin.trim());

        carWash.setStartDayShift(new Time(startShift.getTime()));
        carWash.setFinishDayShift(new Time(finishShift.getTime()));
    }

    @Override
    public CarWash selectCarWash(Integer carWashId) throws SQLException, NamingException {
        return daoFactory.getCarWashDao().selectCarWash(carWashId);
    }

    private String validateCarWashName(String name) throws SQLException {
        if (name == null) return "Укажите название мойки";
        name = name.trim();
        if (name.equals("")) return "Укажите название мойки";
        if (name.length() > CarWashDao.NAME_MAX_LENGTH) return "Слишком большое имя";

        if(daoFactory.getCarWashDao().isCarWashNameExist(name)) return "Данное имя уже используеться";
        return null;
    }

    private String validateCarWashAddress(String address){
        if (address.trim().length() > CarWashDao.ADDRESS_MAX_LENGTH) return "Слишком большое адресс";

        return null;
    }

    private String validateCarWashPhoneNumber (String phoneNumber){
        if (phoneNumber.trim().length() > CarWashDao.PHONE_NUMBER_MAX_LENGTH) return "Слишком длинный телефон";

        return null;
    }



}
