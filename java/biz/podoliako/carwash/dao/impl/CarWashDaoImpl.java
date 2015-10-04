package biz.podoliako.carwash.dao.impl;

import biz.podoliako.carwash.dao.CarWashDao;
import biz.podoliako.carwash.models.entity.CarWash;
import biz.podoliako.carwash.services.exeption.NamingRuntimeException;
import biz.podoliako.carwash.services.exeption.SQLRuntimeException;
import biz.podoliako.carwash.services.impl.ConnectDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class CarWashDaoImpl implements CarWashDao {
    public final static String CAR_WASH_TABLE = "car_wash";

    private Connection conn = null;

    @Autowired
    ConnectDB connectDB;

    @Autowired
    public CarWashDaoImpl(ConnectDB connectDB) throws SQLException, NamingException {
        this.conn = connectDB.getPoolConnection();
    }

    @Override
    public void addCarWash(CarWash carWash) throws SQLException {
        String query = "INSERT INTO " + CAR_WASH_TABLE +
                       "(name, address, box_amount, phone_number, date_of_creation, owner_id, start_shift, finish_shift) VALUES " +
                       "(  ?,      ?,       ?,             ?,              ?,           ?,         ?,            ?     )" ;
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, carWash.getName());
        ps.setString(2, carWash.getAddress());
        ps.setInt(3, carWash.getBoxAmount());
        ps.setString(4, carWash.getPhoneNumber());
        ps.setObject(5, carWash.getDateOfCreation());
        ps.setInt(6, carWash.getOwnerId());
        ps.setTime(7, carWash.getStartDayShift());
        ps.setTime(8, carWash.getFinishDayShift());

        ps.execute();

    }

    @Override
    public void deleteCarWash(Integer carWashId) throws SQLException {
        String query = "DELETE FROM "+ CAR_WASH_TABLE + " WHERE id= ?";

        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, carWashId);
        statement.execute();

    }

    @Override
    public void modifyCarWash() {

    }

    @Override
    public boolean isCarWashNameExist(String name) throws SQLException {
        Boolean result = false;
        String query = "SELECT * FROM " + CAR_WASH_TABLE + " WHERE name = ?";

        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) result = true;

        return result;
    }

    @Override
    public CarWash selectCarWash(Integer id){
        Connection connection = null;
        PreparedStatement ps = null;
        CarWash carWash = new CarWash();

        try {
            connection = connectDB.getPoolConnection();

            String query = "SELECT * FROM " + CAR_WASH_TABLE + " WHERE id = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                carWash.setId(rs.getInt("id"));
                carWash.setName(rs.getString("name"));
                carWash.setAddress(rs.getString("address"));
                carWash.setBoxAmount(rs.getInt("box_amount"));
                carWash.setPhoneNumber(rs.getString("phone_number"));
                carWash.setDateOfCreation(rs.getTimestamp("date_of_creation"));
                carWash.setOwnerId(rs.getInt("owner_id"));
                carWash.setStartDayShift(rs.getTime("start_shift"));
                carWash.setFinishDayShift(rs.getTime("finish_shift"));
            }
        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе isCarWashNameExist (CarWashDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе isCarWashNameExist (CarWashDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {
                    connection.close();
                }
            }catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе isCarWashNameExist (CarWashDaoImpl) " + e);
            }

        }

        return carWash;
    }

    @Override
    public List<CarWash> selectAllCarWash(Integer ownerId) throws SQLException {
        List<CarWash> carWashList = new ArrayList<>();
        String query = "SELECT * FROM " + CAR_WASH_TABLE + " WHERE owner_id = " + ownerId + " ORDER by name";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(query);

        while (rs.next()) {
            CarWash carWash = new CarWash();

            carWash.setId(rs.getInt("id"));
            carWash.setName(rs.getString("name"));
            carWash.setAddress(rs.getString("address"));
            carWash.setBoxAmount(rs.getInt("box_amount"));
            carWash.setPhoneNumber(rs.getString("phone_number"));
            carWash.setDateOfCreation(rs.getTimestamp("date_of_creation"));
            carWash.setOwnerId(rs.getInt("owner_id"));
            carWash.setStartDayShift(rs.getTime("start_shift"));
            carWash.setFinishDayShift(rs.getTime("finish_shift"));

            carWashList.add(carWash);
        }

        return carWashList;
    }
}
