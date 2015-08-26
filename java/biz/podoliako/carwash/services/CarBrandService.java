package biz.podoliako.carwash.services;


import biz.podoliako.carwash.models.entity.CarBrand;

import java.sql.SQLException;
import java.util.List;

public interface CarBrandService {

    void  addCarBrad(CarBrand carBrand) throws SQLException;

    boolean  isCarBrandExist(String name) throws SQLException;


    List<CarBrand> getAllCarBrands(Integer ownerId) throws SQLException;
}
