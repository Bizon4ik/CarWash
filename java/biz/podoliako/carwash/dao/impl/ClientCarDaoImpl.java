package biz.podoliako.carwash.dao.impl;


import biz.podoliako.carwash.dao.ClientCarDao;
import biz.podoliako.carwash.models.entity.Car;
import biz.podoliako.carwash.services.ConnectionDB;
import biz.podoliako.carwash.services.exeption.NamingRuntimeException;
import biz.podoliako.carwash.services.exeption.SQLRuntimeException;
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
}
