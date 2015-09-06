package biz.podoliako.carwash.dao;


import biz.podoliako.carwash.models.entity.Car;

public interface ClientCarDao {
    final static String CARS_TABLE = "car";
    Integer addCar(Car car);

}
