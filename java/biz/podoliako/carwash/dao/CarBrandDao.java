package biz.podoliako.carwash.dao;

import biz.podoliako.carwash.models.entity.CarBrand;

import java.sql.SQLException;
import java.util.List;

public interface CarBrandDao {

    void addCarBrand(CarBrand carBrand) throws SQLException;

    boolean isCarBrandExist(String name) throws SQLException;

    List<CarBrand> selectAllCarBrands(Integer ownerId) throws SQLException;
}
