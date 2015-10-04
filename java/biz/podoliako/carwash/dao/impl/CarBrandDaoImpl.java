package biz.podoliako.carwash.dao.impl;

import biz.podoliako.carwash.dao.CarBrandDao;
import biz.podoliako.carwash.models.entity.CarBrand;
import biz.podoliako.carwash.services.ConnectionDB;
import biz.podoliako.carwash.services.exeption.NamingRuntimeException;
import biz.podoliako.carwash.services.exeption.SQLRuntimeException;
import biz.podoliako.carwash.services.impl.ConnectDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CarBrandDaoImpl implements CarBrandDao{

   private ConnectionDB connectionDB;

    @Autowired
    public CarBrandDaoImpl(ConnectionDB connectionDB) {
        this.connectionDB = connectionDB;

    }

    @Override
    public void addCarBrand(CarBrand carBrand) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionDB.getConnection();

            String query = "INSERT INTO " + CAR_BRAND_TABLE + "" +
                    "(name, created_by, date_of_creation, date_of_delete, owner_id) VALUES " +
                    "(?,       ?,             ?,              NULL,              ?   )";

            ps = connection.prepareStatement(query);
            ps.setString(1, carBrand.getName());
            ps.setInt(2, carBrand.getCreatedBy());
            ps.setObject(3, carBrand.getDateOfcreation());
            ps.setInt(4, carBrand.getOwnerId());

            ps.execute();

        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе addCarBrand (CarBrandDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе addCarBrand (CarBrandDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {
                    connection.close();
                }
            }catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе addCarBrand (CarBrandDaoImpl) " + e);
            }
        }

    }

    @Override
    public boolean isCarBrandExist(String name) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionDB.getConnection();

            String query = "SELECT * FROM " + CAR_BRAND_TABLE + " WHERE  name = ?";

            ps = connection.prepareStatement(query);
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе isCarBrandExist (CarBrandDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе isCarBrandExist (CarBrandDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {
                    connection.close();
                }
            }catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе isCarBrandExist (CarBrandDaoImpl) " + e);
            }
        }

    }

    @Override
    public List<CarBrand> selectAllCarBrands(Integer ownerId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        List<CarBrand> carBrands = new ArrayList<>();

        try {
            connection = connectionDB.getConnection();

            String query = "SELECT * FROM " + CAR_BRAND_TABLE + " WHERE owner_id = ? AND date_of_delete IS NULL ORDER BY name";

            ps = connection.prepareStatement(query);
            ps.setInt(1, ownerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CarBrand carBrand = new CarBrand();
                carBrand.setId(rs.getInt("id"));
                carBrand.setName(rs.getString("name"));
                carBrand.setCreatedBy(rs.getInt("created_by"));
                carBrand.setDateOfcreation(rs.getTimestamp("date_of_creation"));
                carBrand.setDateOfdelete(null);
                carBrand.setOwnerId(rs.getInt("owner_id"));

                carBrands.add(carBrand);
            }
            return carBrands;

        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе selectAllCarBrands (CarBrandDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе selectAllCarBrands (CarBrandDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {
                    connection.close();
                }
            }catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе selectAllCarBrands (CarBrandDaoImpl) " + e);
            }
        }

    }

    @Override
    public CarBrand selectBrandById(Integer carBrandId) {
        Connection connection = null;
        PreparedStatement ps = null;
        CarBrand carBrand = new CarBrand();

        try {
            connection = connectionDB.getConnection();

            String query = "SELECT " +
                    " id, name, created_by, date_of_creation, date_of_delete, owner_id" +
                    " FROM " + CAR_BRAND_TABLE  +
                    " WHERE id =?"  ;

            ps = connection.prepareStatement(query);
            ps.setInt(1, carBrandId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                carBrand.setId(rs.getInt("id"));
                carBrand.setName(rs.getString("name"));
                carBrand.setCreatedBy(rs.getInt("created_by"));
                carBrand.setDateOfcreation(rs.getTimestamp("date_of_creation"));
                carBrand.setDateOfdelete(rs.getTimestamp("date_of_delete"));
                carBrand.setOwnerId(rs.getInt("owner_id"));
            }
            return carBrand;

        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе selectBrandById (CarBrandDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе selectBrandById (CarBrandDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {

                    connection.close();
                }
            }catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе selectBrandById (CarBrandDaoImpl) " + e);
            }
        }

    }
}
