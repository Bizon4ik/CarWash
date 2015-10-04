package biz.podoliako.carwash.dao;

import biz.podoliako.carwash.models.entity.CarBrand;

import java.sql.SQLException;
import java.util.List;

public interface CarBrandDao {
    public static String CAR_BRAND_TABLE = "car_brand";

    void addCarBrand(CarBrand carBrand) throws SQLException;

    boolean isCarBrandExist(String name) throws SQLException;

    List<CarBrand> selectAllCarBrands(Integer ownerId) throws SQLException;

    CarBrand selectBrandById(Integer carBrandId);
}
