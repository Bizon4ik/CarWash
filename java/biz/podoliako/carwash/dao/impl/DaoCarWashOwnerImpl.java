package biz.podoliako.carwash.dao.impl;

import biz.podoliako.carwash.dao.DaoCarWashOwner;
import biz.podoliako.carwash.dao.pojo.CarWashOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@Qualifier("DaoCarWashOwner")
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class DaoCarWashOwnerImpl implements DaoCarWashOwner {

    private Connection connection = null;

    @Autowired
    public DaoCarWashOwnerImpl(ConnectDB connectDB) throws SQLException, NamingException {
       this.connection = connectDB.getPoolConnection();
       /* this.connection = connectDB.getOneConnection();*/
    }


    @Override
    public CarWashOwner selectCarWashOwner(String login, String password) throws SQLException {
        CarWashOwner carWashOwner = null;

        String query = "SELECT * FROM car_wash_owners WHERE login = ? AND  password = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, login);
        statement.setString(2, password);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            carWashOwner = new CarWashOwner();
            carWashOwner.setId(resultSet.getInt("id"));
            carWashOwner.setName(resultSet.getString("name"));
            carWashOwner.setPhoneNumber(resultSet.getString("phone_number"));
            carWashOwner.setLogin(resultSet.getString("login"));
        }

        return carWashOwner;
    }

    public String str() {
        return  "Yep!";
    }
}
