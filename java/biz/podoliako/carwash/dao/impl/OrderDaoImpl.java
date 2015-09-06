package biz.podoliako.carwash.dao.impl;

import biz.podoliako.carwash.dao.OrderDao;
import biz.podoliako.carwash.services.ConnectionDB;
import biz.podoliako.carwash.services.exeption.NamingRuntimeException;
import biz.podoliako.carwash.services.exeption.SQLRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component("OrderDao")
public class OrderDaoImpl implements OrderDao{


    ConnectionDB connectionDB;

    @Autowired
    public OrderDaoImpl(ConnectionDB connectionDB) {
        this.connectionDB = connectionDB;
    }


    @Override
    public List<Integer> selectAvailableBox(Integer carWashId) {
        Connection connection = null;
        PreparedStatement ps = null;
        List<Integer> availableBoxes = new ArrayList<>();

        try {
            connection = connectionDB.getConnection();

            String query = "SELECT DISTINCT box_number " +
                           "FROM " + UserDaoImpl.WASHER_MAN_IN_BOX_TABLE +
                           " WHERE finish IS NULL " +
                           "AND car_wash_id = ? " +
                           "ORDER BY box_number";

            ps = connection.prepareStatement(query);
            ps.setInt(1, carWashId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                availableBoxes.add(rs.getInt("box_number"));
            }

        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе selectAvailableBox (OrderDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе selectAvailableBox (OrderDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {

                    connection.close();
                }
            }catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе selectAvailableBox (OrderDaoImpl) " + e);
            }
        }

        return availableBoxes;
    }
}
