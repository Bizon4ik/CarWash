package biz.podoliako.carwash.services.impl;


import biz.podoliako.carwash.dao.DaoFactory;
import biz.podoliako.carwash.models.entity.CarBrand;
import biz.podoliako.carwash.services.CarBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Service
public class CarBrandServiceImpl implements CarBrandService{

    @Autowired
    private DaoFactory daoFactory;

    @Override
    public void addCarBrad(CarBrand carBrand) throws SQLException {
        carBrand.setName(carBrand.getName().trim().toLowerCase());
        carBrand.setDateOfcreation(new Date());

        daoFactory.getCarBrandDao().addCarBrand(carBrand);

    }

    @Override
    public boolean isCarBrandExist(String name) throws SQLException {

        return daoFactory.getCarBrandDao().isCarBrandExist(name.trim().toLowerCase());
    }

    @Override
    public List<CarBrand> getAllCarBrands(Integer ownerId) throws SQLException {
        return daoFactory.getCarBrandDao().selectAllCarBrands(ownerId);
    }


}
