package biz.podoliako.carwash.dao;


import biz.podoliako.carwash.models.entity.Car;
import biz.podoliako.carwash.view.CarViewWithClient;

public interface ClientCarDao {
    final static String CARS_TABLE = "car";
    Integer addCar(Car car);

    CarViewWithClient selectCarWithClient(Integer carId);

    Car selectCarByNumber(String number);

}
