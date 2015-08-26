package biz.podoliako.carwash.dao.impl;

import biz.podoliako.carwash.dao.CarBrandDao;
import biz.podoliako.carwash.models.entity.CarBrand;
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
    public static String CAR_BRAND_TABLE = "car_brand";

    private Connection connection;

    @Autowired
    public CarBrandDaoImpl(ConnectDB connectDB) throws SQLException, NamingException {
        this.connection = ConnectDB.getPoolConnection();
    }

    public CarBrandDaoImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public void addCarBrand(CarBrand carBrand) throws SQLException {
        String query = "INSERT INTO " + CAR_BRAND_TABLE + "" +
                       "(name, created_by, date_of_creation, date_of_delete, owner_id) VALUES " +
                       "(?,       ?,             ?,              NULL,              ?   )";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, carBrand.getName());
        ps.setInt(2, carBrand.getCreatedBy());
        ps.setObject(3, carBrand.getDateOfcreation());
        ps.setInt(4, carBrand.getOwnerId());

        ps.execute();

    }

    @Override
    public boolean isCarBrandExist(String name) throws SQLException {
        String query = "SELECT * FROM " + CAR_BRAND_TABLE + " WHERE  name = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, name);

        ResultSet rs = ps.executeQuery();

        return rs.next();
    }

    @Override
    public List<CarBrand> selectAllCarBrands(Integer ownerId) throws SQLException {
        String query = "SELECT * FROM " + CAR_BRAND_TABLE + " WHERE owner_id = ? AND date_of_delete IS NULL ORDER BY name";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, ownerId);
        ResultSet rs = ps.executeQuery();

        List<CarBrand> carBrands = new ArrayList<>();

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
    }
}
