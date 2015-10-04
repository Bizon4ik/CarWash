package biz.podoliako.carwash.dao.impl;


import biz.podoliako.carwash.dao.CarBrandDao;
import biz.podoliako.carwash.dao.CategoryDao;
import biz.podoliako.carwash.dao.ClientCarDao;
import biz.podoliako.carwash.models.entity.Car;
import biz.podoliako.carwash.services.ConnectionDB;
import biz.podoliako.carwash.services.exeption.NamingRuntimeException;
import biz.podoliako.carwash.services.exeption.SQLRuntimeException;
import biz.podoliako.carwash.view.CarViewWithClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import java.sql.*;

@Component("ClientCarDao")
public class ClientCarDaoImpl implements ClientCarDao{

    ConnectionDB connectionDB;

    @Autowired
    public ClientCarDaoImpl(ConnectionDB connectionDB) {
        this.connectionDB = connectionDB;
    }

    @Override
    public Integer addCar(Car car) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionDB.getConnection();

            String query = "INSERT INTO " + CARS_TABLE +
                           "(car_number, category_id, brand_id, client_id, is_final) VALUES " +
                           "(   ?,             ?,        ?,          ?,       ?    )";

            ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, car.getNumber());
            ps.setInt(2, car.getCategoryId());
            ps.setInt(3, car.getBrandId());
            ps.setObject(4, car.getClientId());
            ps.setBoolean(5, car.getIsFinal());

            ps.execute();

            ResultSet generatedKey = ps.getGeneratedKeys();
            if (generatedKey.next()) {
                return (int) generatedKey.getLong(1);
            }
            else {
                throw new SQLException("Creating car failed, can not obtain added car ID");
            }


        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе addCar (ClientCarDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе addCar (ClientCarDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {

                    connection.close();
                }
            }catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе addCar (ClientCarDaoImpl) " + e);
            }
        }


    }

    @Override
    public CarViewWithClient selectCarWithClient(Integer carId) {
        Connection connection = null;
        PreparedStatement ps = null;
        CarViewWithClient carViewWithClient = new CarViewWithClient();

        try {
            connection = connectionDB.getConnection();

            String query = "SELECT " +
                                " car.id as c_id, car_number, category_id, brand_id, client_id, is_final," +
                                " b.name as b_name, "+
                                " cat.name as cat_name, cat.description as cat_description "+
                           " FROM " + CARS_TABLE + " as car" +
                           " JOIN " + CarBrandDao.CAR_BRAND_TABLE + " as b " +
                                    " ON car.brand_id = b.id" +
                           " JOIN " + CategoryDao.CATEGORY_TABLE_NAME + " as cat"+
                                    " ON car.category_id = cat.id "+
                           " WHERE car.id = ?"  ;

            ps = connection.prepareStatement(query);
            ps.setInt(1, carId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                carViewWithClient.setId(rs.getInt("c_id"));
                carViewWithClient.setNumber(rs.getString("car_number"));
                carViewWithClient.setCategoryId(rs.getInt("category_id"));
                carViewWithClient.setBrandId(rs.getInt("brand_id"));
                carViewWithClient.setClientId(rs.getInt("client_id"));
                carViewWithClient.setIsFinal(rs.getBoolean("is_final"));
                carViewWithClient.setBrandName(rs.getString("b_name"));
                carViewWithClient.setCategoryName(rs.getString("cat_name"));
                carViewWithClient.setCategotyDescription(rs.getString("cat_description"));

            }

            return carViewWithClient;

        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе addCar (ClientCarDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе addCar (ClientCarDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {

                    connection.close();
                }
            }catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе addCar (ClientCarDaoImpl) " + e);
            }
        }
    }

    @Override
    public Car selectCarByNumber(String number) {
        Connection connection = null;
        PreparedStatement ps = null;
        Car car = new Car();

        try {
            connection = connectionDB.getConnection();

            String query = "SELECT " +
                    " id, car_number, category_id, brand_id, client_id, is_final" +
                    " FROM " + CARS_TABLE  +
                    " WHERE car_number = ? ";

            ps = connection.prepareStatement(query);
            ps.setString(1, number);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                car.setId(rs.getInt("id"));
                car.setNumber(rs.getString("car_number"));
                car.setCategoryId(rs.getInt("category_id"));
                car.setBrandId(rs.getInt("brand_id"));
                car.setClientId(rs.getInt("client_id"));
                car.setIsFinal(rs.getBoolean("is_final"));
            }
            return car;

        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе selectCarByNumber (ClientCarDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе selectCarByNumber (ClientCarDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {

                    connection.close();
                }
            }catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе selectCarByNumber (ClientCarDaoImpl) " + e);
            }
        }
    }
}
